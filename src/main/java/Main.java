
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
	public static void main(String[] args) throws IOException {

		// navigate to the main URL
		Document mainDoc = Jsoup.connect("https://www.bigelowtea.com/Teas/Flavors").get();
		log(mainDoc.title());

		// loop through each flavor category on the main page
		Elements flavorLinks = mainDoc.select("div.product-item > a");
		for (Element flavorLink : flavorLinks)
		{
			// get the URL for each flavor
			// System.out.println(flavorLink.absUrl("href"));
			String flavorURL = flavorLink.absUrl("href");
			//String flavorName = flavorLink.select("h3").text();
			//System.out.println(flavorName);

			// navigate to flavor page
			Document flavorDoc = Jsoup.connect(flavorURL).get();

			// save the flavor name from the heading to use below
			String flavorName = flavorDoc.select("h1").text();

			// flavor section seperator
			System.out.println();
			System.out.println("*********************************");

			// loop through the flavor page and get URL for each product
			Elements productLinks = flavorDoc.select("a.product-name");

			for (Element productLink : productLinks)
			{
				// System.out.println(productLink.absUrl("href"));
				String productURL = productLink.absUrl("href");

				// navigate to product page
				Document productDoc = Jsoup.connect(productURL).get();

				//blank line separator
				System.out.println();

				// product parent flavor
				System.out.println(flavorName + " Flavor Available:");

				// product name
				Elements heading = productDoc.select("h1");
				System.out.println("Name:\t\t\t" + heading.text());

				// product sku
				Elements sku = productDoc.select("div.product-sku");
				System.out.println("SKU:\t\t\t" + sku.text());

				// product description
				Elements description = productDoc.select("div.product-description");
				System.out.println("Description:\t" + description.text());

				// product tags
				System.out.print("Tags:\t\t\t");
				Elements tags = productDoc.select("div.tags ul li a");
				for (Element tag : tags)
				{
					// String tagname = tag.select("ul li a");
					//System.out.println(tagname);
					System.out.print(tag.text() + ", ");

				}

				// product separator
				System.out.println();

			} // end of looping through the products on the flavor page
		} // end looping through flavor categories on main page

	}

	private static void log(String msg, String... vals) {
		System.out.println(String.format(msg, vals));
	}
}