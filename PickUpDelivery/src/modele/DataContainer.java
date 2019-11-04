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
			Plan planTemp = new Plan();
			this.xmlFile = new File(XMLPath);
			if (!xmlFile.isFile()){
				System.out.println("Le Ficher XML n'existe pas");
				return false;
			}
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			// System.out.println("Root element :" +
			// doc.getDocumentElement().getNodeName());
			NodeList listeNoeud = doc.getElementsByTagName("noeud");
			if (listeNoeud.getLength() < 2) {
				System.out.println(
						"Ficher XML non valide: xml plan doit impérativement contenir au moins 2 noeuds et au moins 1 troncon");
				return false;
			}

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
					planTemp.AjouterNoeud(unNode);
				}
			}

			NodeList listeTroncon = doc.getElementsByTagName("troncon");
			if (listeTroncon.getLength() < 1) {
				System.out.println(
						"Ficher XML non valide: xml plan doit impérativement contenir au moins 2 noeuds et au moins 1 troncon");
				return false;
			}

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
					Noeud origine = planTemp.ChercherNoeudSelonId(origineId);
					Noeud destination = planTemp.ChercherNoeudSelonId(destinationId);
					if (origine == null || destination == null) {
						System.out.println("Noeud origine ou destination introuvable.");
						return false;
					}
					Troncon unTroncon = new Troncon(origine, destination, nomRue, longueur);
					planTemp.AjouterTroncon(unTroncon);
					origine.AjouterTroncon(unTroncon);
				}
			}
			plan = planTemp;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean chargerDemandeLivraison(String XMLPath) throws Exception {
		try {
			if (plan.getNoeuds().isEmpty()) {
				System.out.println("Il faut charger un plan avant de charger les demandes de livraison.");
				return false;
			}

			DemandeLivraison demandeLivraisonTemp = new DemandeLivraison();
			this.xmlFile = new File(XMLPath);
			if (!xmlFile.isFile()){
				System.out.println("Le Ficher XML n'existe pas");
				return false;
			}
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			// System.out.println("Root element :" +
			// doc.getDocumentElement().getNodeName());

			NodeList entrepot = doc.getElementsByTagName("entrepot");
			if (entrepot.getLength() != 1) {
				System.out.println("Ficher XML non valide: xml demande doit impérativement contenir 1 entrepot");
				return false;
			}
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

					Entrepot unEntrepot = new Entrepot(idNoeudEntrepot, noeudEntrepot.GetLatitude(),
							noeudEntrepot.GetLongitude(), heureDepart);
					demandeLivraisonTemp.setEntrepotLivraison(unEntrepot);

				}
			}

			NodeList listeLivraison = doc.getElementsByTagName("livraison");
			if (listeLivraison.getLength() < 1) {
				System.out.println(
						"Ficher XML non valide: xml demande doit impérativement contenir au moins 1 livraison");
				return false;
			}

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
					demandeLivraisonTemp.AjouterLivraison(uneLivraison);
				}
			}
			demandeLivraison = demandeLivraisonTemp;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}