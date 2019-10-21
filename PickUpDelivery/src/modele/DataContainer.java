package modele;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DataContainer {
	private File xmlFile;
	private DemandeLivraison demandeLivraison;
	private Plan plan;
	// private DemandeLivraison demandeLivraison;

	public DataContainer() {
		this.plan = new Plan();
		this.demandeLivraison = new DemandeLivraison();
	}

	public Plan GetPlan() {
		return plan;
	}

	public DemandeLivraison GetDemandeLivraison() {
		return demandeLivraison;
	}

	public boolean chargerPlan(String XMLPath) throws Exception {
		try {
			this.xmlFile = new File(XMLPath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			NodeList listeNoeud = doc.getElementsByTagName("noeud");
			System.out.println("----------------------------");
			for (int temp = 0; temp < listeNoeud.getLength(); temp++) {
				Node xmlNode = listeNoeud.item(temp);
				System.out.println("\nCurrent Element :" + xmlNode.getNodeName());
				if (xmlNode.getNodeType() == Node.ELEMENT_NODE) {
					Element xmlElement = (Element) xmlNode;
					System.out.println("noeud id : " + xmlElement.getAttribute("id"));
					System.out.println("latitude : " + xmlElement.getAttribute("latitude"));
					System.out.println("longitude: " + xmlElement.getAttribute("longitude"));
					String idNoeud = xmlElement.getAttribute("id");
					String latitudeString = xmlElement.getAttribute("latitude");
					String longitudeString = xmlElement.getAttribute("longitude");
					double latitude = Double.parseDouble(latitudeString);
					double longitude = Double.parseDouble(longitudeString);
					Noeud unNode = new Noeud(idNoeud, latitude, longitude);
					plan.AjouterNoeud(unNode);
				}
			}

			NodeList listeTroncon = doc.getElementsByTagName("troncon");
			System.out.println("----------------------------");
			for (int temp = 0; temp < listeTroncon.getLength(); temp++) {
				Node xmlNode = listeTroncon.item(temp);
				System.out.println("\nCurrent Element :" + xmlNode.getNodeName());
				if (xmlNode.getNodeType() == Node.ELEMENT_NODE) {
					Element xmlElement = (Element) xmlNode;
					System.out.println("longueur : " + xmlElement.getAttribute("longueur"));

					System.out.println("nom rue : " + xmlElement.getAttribute("nomRue"));
					System.out.println("origine : " + xmlElement.getAttribute("origine"));
					System.out.println("destination : " + xmlElement.getAttribute("destination"));
					String longueurString = xmlElement.getAttribute("longueur");
					double longueur = Double.parseDouble(longueurString);
					String nomRue = xmlElement.getAttribute("nomRue");
					String origineId = xmlElement.getAttribute("origine");
					String destinationId = xmlElement.getAttribute("destination");
					Noeud origine = plan.ChercherNoeudSelonId(origineId);
					Noeud destination = plan.ChercherNoeudSelonId(destinationId);
					if (origine == null || destination == null) {
						System.out.println("Noeud origine ou destination introuvable.");
						return false;
					}
					Troncon unTroncon = new Troncon(origine, destination, nomRue, longueur);
					plan.AjouterTroncon(unTroncon);
				}

			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean chargerDemandeLivraison(String XMLPath) throws Exception {
		try {
			this.xmlFile = new File(XMLPath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

			NodeList entrepot = doc.getElementsByTagName("entrepot");
			System.out.println("----------------------------");
			for (int temp = 0; temp < entrepot.getLength(); temp++) {
				Node xmlNode = entrepot.item(temp);
				System.out.println("\nCurrent Element :" + xmlNode.getNodeName());
				if (xmlNode.getNodeType() == Node.ELEMENT_NODE) {
					Element xmlElement = (Element) xmlNode;
					System.out.println("noeud entrepot id  : " + xmlElement.getAttribute("adresse"));
					System.out.println("heure de depart de l'entrepot : " + xmlElement.getAttribute("heureDepart"));

					String idNoeudEntrepot = xmlElement.getAttribute("adresse");
					String heureDepart = xmlElement.getAttribute("heureDepart");
					Noeud noeudEntrepot = plan.ChercherNoeudSelonId(idNoeudEntrepot);
					if (noeudEntrepot == null) {
						System.out.println("Noeud origine introuvable.");
						return false;
					}

					Entrepot unEntrepot = new Entrepot(idNoeudEntrepot,noeudEntrepot.GetLatitude(),noeudEntrepot.GetLongitude(),heureDepart);
					demandeLivraison.setEntrepotLivraison(unEntrepot);

				}

			}

			NodeList listeLivraison = doc.getElementsByTagName("livraison");
			System.out.println("----------------------------");
			for (int temp = 0; temp < listeLivraison.getLength(); temp++) {
				Node xmlNode = listeLivraison.item(temp);
				System.out.println("\nCurrent Element :" + xmlNode.getNodeName());
				if (xmlNode.getNodeType() == Node.ELEMENT_NODE) {
					Element xmlElement = (Element) xmlNode;
					System.out.println("Noeud Enlevement : " + xmlElement.getAttribute("adresseEnlevement"));
					System.out.println("Noeud Livraison : " + xmlElement.getAttribute("adresseLivraison"));
					System.out.println("dureeEnlevement : " + xmlElement.getAttribute("dureeEnlevement"));
					System.out.println("dureeLivraison: " + xmlElement.getAttribute("dureeLivraison"));
					String idNoeudEnlevement = xmlElement.getAttribute("adresseEnlevement");
					String idNoeudLivraison = xmlElement.getAttribute("adresseLivraison");
					String dureeEnlevementString = xmlElement.getAttribute("dureeEnlevement");
					String dureeLivraisonString = xmlElement.getAttribute("dureeLivraison");
					int dureeEnlevement = Integer.parseInt(dureeEnlevementString);
					int dureeLivraison = Integer.parseInt(dureeLivraisonString);
					Noeud origine = plan.ChercherNoeudSelonId(idNoeudEnlevement);
					Noeud destination = plan.ChercherNoeudSelonId(idNoeudLivraison);
					if (origine == null || destination == null) {
						System.out.println("Noeud origine ou destination introuvable.");
						return false;
					}
					Livraison uneLivraison = new Livraison(origine, destination, dureeEnlevement, dureeLivraison);
					demandeLivraison.AjouterLivraison(uneLivraison);
				}
			}

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/*
	 * public boolean chargerDemande(String XMLPath) { try { this.xmlFile = new
	 * File(XMLPath); DocumentBuilderFactory dbFactory =
	 * DocumentBuilderFactory.newInstance(); DocumentBuilder dBuilder =
	 * dbFactory.newDocumentBuilder(); Document doc = dBuilder.parse(xmlFile);
	 * doc.getDocumentElement().normalize(); System.out.println("Root element :" +
	 * doc.getDocumentElement().getNodeName()); NodeList listeEntrepot =
	 * doc.getElementsByTagName("entrepot"); Node xmlNode = listeEntrepot.item(0);
	 * if (xmlNode.getNodeType() == Node.ELEMENT_NODE) { Element xmlElement =
	 * (Element) xmlNode; String entrepotId = xmlElement.getAttribute("adresse");
	 * String heureDepart = xmlElement.getAttribute("heureDepart"); Noeud
	 * entrepotNoeud = plan.ChercherNoeudSelonId(entrepotId); Entrepot entrepot =
	 * new Entrepot(entrepotNoeud, heureDepart);
	 * demandeLivraison.SetEntrepot(entrepot); }
	 * 
	 * NodeList listeLivraison = doc.getElementsByTagName("livraison"); for (int
	 * temp = 0; temp < listeLivraison.getLength(); temp++) { xmlNode =
	 * listeLivraison.item(temp); System.out.println("\nCurrent Element :" +
	 * xmlNode.getNodeName()); if (xmlNode.getNodeType() == Node.ELEMENT_NODE) {
	 * Element xmlElement = (Element) xmlNode; String adresseEnlevementId =
	 * xmlElement.getAttribute("adresseEnlevement"); String adresseLivraisonId =
	 * xmlElement.getAttribute("adresseLivraison"); int dureeEnlevement =
	 * Integer.parseInt(xmlElement.getAttribute("dureeEnlevement")); int
	 * dureeLivraison = Integer.parseInt(xmlElement.getAttribute("dureeLivraison"));
	 * Noeud adresseEnlevement = plan.ChercherNoeudSelonId(adresseEnlevementId);
	 * Noeud adresseLivraison = plan.ChercherNoeudSelonId(adresseLivraison);
	 * Livraison unLivraison = new Livraison(adresseEnlevement, adresseLivraison,
	 * dureeEnlevement, dureeLivraison);
	 * demandeLivraison.AjouterLivraison(unLivraison); } }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } }
	 */

}