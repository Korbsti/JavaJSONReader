import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JSONMain {

	private String path;

	public JSONMain(String path) {
		this.path = path;

	}
  // create the class, after that call "getData" the string should be something like this d.d.a
  // if it ends with a dot, such as d.d. it will return everything in the "d.d."

	public Object getData(String data) {

		try {
			BufferedReader read = new BufferedReader(new FileReader(path));

			String str = "";
			String add = "";
			boolean addOnto = false;
			boolean correctArea = false;
			boolean insideArea = false;
			String tempData = data;
			while ((str = read.readLine()) != null) {
				str = str.replace("\t", " ");
				if (addOnto) {
					if (!str.replace(" ", "").endsWith("}")) {
						add += str;
					} else {
						add += str;
						return (Object) reduceSpace(add);

						
					}
				} else if (!str.equals("{") && !str.equals("}")) {
					if (data.split("\\.").length >= 1) {
						if (str.replace(" ", "").startsWith(data.substring(0, findFirstPosition(data, '.'))) && str.endsWith("{")) {
							correctArea = true;
						}
						if(correctArea && insideArea) {
							if (str.replace(" ", "").startsWith(tempData) && !str.endsWith("}") && !tempData.endsWith(".")) {
								if(!str.substring(findFirstPosition(str, ":".toCharArray()[0]) + 2,
										str.length() - 1).endsWith("]") || !str.substring(findFirstPosition(str, ":".toCharArray()[0]) + 2,
												str.length() - 1).endsWith("}")) {
									return ((Object) str.substring(findFirstPosition(str, ":".toCharArray()[0]) + 2,
											str.length() - 1));
								}
								return ((Object) str.substring(findFirstPosition(str, ":".toCharArray()[0]) + 2,
										str.length()));
							}
							if (tempData.endsWith(".")) {
								if (str.replace(" ", "").startsWith(tempData.substring(0, tempData.length() - 1))
										&& str.endsWith("{") && !addOnto) {
									addOnto = true;
									add += "{";
								}
							}
							
						}
						if(correctArea && tempData.split("\\.").length != 1) {
							tempData = tempData.substring(findFirstPosition(tempData, '.') + 1, tempData.length());
							insideArea = true;
							
							
						}
					} else {

						if (str.replace(" ", "").startsWith(data) && !str.endsWith("}") && !data.endsWith(".")) {
							return ((Object) str.substring(findFirstPosition(str, ":".toCharArray()[0]) + 2,
									str.length() - 1));
						}
						if (data.endsWith(".")) {
							if (str.replace(" ", "").startsWith(data.substring(0, data.length() - 1))
									&& str.endsWith("{") && !addOnto) {
								addOnto = true;
								add += "{";
							}
						}
					}
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	public int findFirstPosition(String str, char find) {
		int x = 0;
		for (char ch : str.toCharArray()) {
			if (ch == find) {
				return x;
			}
			x++;
		}
		return x;
	}

	public String reduceSpace(String str) {
		String replaced = "";
		boolean isQuotation = false;
		for (char ch : str.toCharArray()) {
			if (ch != ' ' && !isQuotation || (isQuotation)) {
				replaced += ch;
			}
			if (("" + ch).equals("\"") && !isQuotation) {
				isQuotation = true;
			} else if (("" + ch).equals("\"") && isQuotation) {
				isQuotation = false;

			}
		}

		return replaced;
	}

}
