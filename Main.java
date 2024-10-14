import java.util.*;

public class Main {	
	static String[] itemCategories = new String[100];
	static String[][] items = new String[100][6];
	static String[][] suppliers = new String[100][2];
	static boolean addingCategory = false;
	static boolean exists = false;
	static boolean userNameCorrect = false;
	static boolean loginSuccessful = false;
	static String uName = "hs";
	static String psswrd = "123";
	
	public static void rankItemsPerUnitPrice(int choice) {
		Scanner input = new Scanner(System.in);
		System.out.printf("+-----------------------------------------------------------------------+%n");
		System.out.printf("\033[1m|\t\t\tRANKED UNIT PRICE\t\t\t\t|\033[0m%n");
		System.out.printf("+-----------------------------------------------------------------------+%n");
		System.out.println("");
		System.out.printf("+----------+----------+------------+----------+----------+------------+%n");
		System.out.printf("| %-8s | %-8s | %-10s | %-8s | %-8s | %-10s |%n", "SID", "CODE", "DESC", "PRICE", "QTY", "CAT");
		System.out.printf("+----------+----------+------------+----------+----------+------------+%n");
		
		int itemCount = 0;
        for (String[] item : items) {
            if (item != null && item[0] != null) {
                itemCount++;
            }
        }
        
        String[][] sortedItems = new String[itemCount][];
        int index = 0;
        for (String[] item : items) {
            if (item != null && item[0] != null) {
                sortedItems[index++] = item;
            }
        }

		for (int i = 0; i < sortedItems.length - 1; i++) {
            if(sortedItems[i] != null){
				for (int j = 0; j < sortedItems.length - i - 1; j++) {
					double priceA = Double.parseDouble(sortedItems[j][3]);
					double priceB = Double.parseDouble(sortedItems[j + 1][3]);
					if (priceA > priceB) {
						String[] temp = sortedItems[j];
						sortedItems[j] = sortedItems[j + 1];
						sortedItems[j + 1] = temp;
					}
				}
			}
        }
		
		for (int i = 0; i < sortedItems.length; i++) {
            if (sortedItems[i] != null) {
                System.out.printf("| %-8s | %-8s | %-10s | %-8s | %-8s | %-10s |%n", sortedItems[i][5], sortedItems[i][0], sortedItems[i][1], Double.valueOf(sortedItems[i][3]), sortedItems[i][2], itemCategories[Integer.parseInt(sortedItems[i][4])-1]);
            }
        }
		System.out.printf("+----------+----------+------------+----------+----------+------------+%n");
		System.out.print("\nDo you want to go stock manage page? (Y/N) : ");
		char goBack = input.next().charAt(0);

		if (goBack == 'Y' || goBack == 'y') {
			clearConsole();
			stockManagement(goBack);
		} else if (goBack == 'N' || goBack == 'n') {
			clearConsole();
			homePage();
		} else {
			throw new RuntimeException("You can only use 'Y' or 'N'");
		}
	}
	
	public static void viewItems(int choice) {
		boolean viewingItems = false;
		System.out.printf("+--------------------------------------------------------------------+%n");
		System.out.printf("\033[1m|\t\t\t\tVIEW ITEMS\t\t\t\t|\033[0m%n");
		System.out.printf("+--------------------------------------------------------------------+%n");
		System.out.println("");
		
		while (!viewingItems) {
			Scanner input = new Scanner(System.in);			
			for (int i = 0; i < itemCategories.length; i++) {
				if (itemCategories[i] != null) {
					System.out.print(itemCategories[i] + " : ");
					System.out.println("");
					System.out.printf("+----------+----------+------------+----------+----------+%n");
					System.out.printf("| %-8s | %-8s | %-10s | %-8s | %-8s |%n", "SID", "CODE", "DESC", "PRICE", "QTY");
					System.out.printf("+----------+----------+------------+----------+----------+%n");
					for (int j = 0; j < items.length; j++) {
						String description = items[j][0];
						String unitPrice = items[j][1];
						String qty = items[j][2];
						String code = items[j][3];
						if (items[j][0] != null &&i == Integer.parseInt(items[j][4])-1) {
							System.out.printf("| %-8s | %-8s | %-10s | %-8s | %-8s |%n", items[j][5], items[j][0], items[j][1], Double.valueOf(items[j][3]), items[j][4]);
						}	
					}
					System.out.printf("+----------+----------+------------+----------+----------+%n");
					System.out.println("");
				}
			}
					
			System.out.print("\nDo you want to go stock manage page? (Y/N) : ");
			char goBack = input.next().charAt(0);

			if (goBack == 'Y' || goBack == 'y') {
				clearConsole();
				stockManagement(goBack);
			} else if (goBack == 'N' || goBack == 'n') {
				clearConsole();
				homePage();
			} else {
				throw new RuntimeException("You can only use 'Y' or 'N'");
			}
		}
	}
	
	public static void getItemsSupplierWise(int choice) {
		boolean searchSupp = false;
		System.out.printf("+-----------------------------------------------------------------------+%n");
		System.out.printf("\033[1m|\t\t\tSEARCH ITEMS SUPPLIER WISE\t\t\t|\033[0m%n");
		System.out.printf("+-----------------------------------------------------------------------+%n");
		
		while (!searchSupp) {
			Scanner input = new Scanner(System.in);
			System.out.print("\nSupplier ID   : ");
			String supplierId = input.nextLine();
			
			int supplierIndex = -1;		
			for (int i = 0; i < suppliers.length; i++) {
				if (suppliers[i][0] != null && suppliers[i][0].equals(supplierId)) {
					supplierIndex = i;
					System.out.print("Supplier Name : " + suppliers[supplierIndex][1]);
					exists = true;
					break;
				}
			}
			if (!exists) {
				System.out.println("Can't find supplier id. Try again!");
				continue;
			}
			System.out.println("");
			System.out.printf("+------------+-------------+--------------+--------------+--------------+%n");
			System.out.printf("| %-10s | %-10s | %-12s | %-12s | %-12s |%n", "ITEM CODE", "DESCRIPTION", "UNIT PRICE", "QTY ON HAND", "CATEGORY");
			System.out.printf("+------------+-------------+--------------+--------------+--------------+%n");
		
			for (int i = 0; i < items.length; i++) {
				if (items[i][5]!= null && items[i][5].equals(supplierId)) {
					if (itemCategories[i] != null) {
						System.out.printf("| %-10s | %-11s | %-12s | %-12s | %-12s |%n", items[i][0], items[i][1], Double.valueOf(items[i][3]), items[i][2], itemCategories[Integer.parseInt(items[i][4])-1]);
					}
				}
			}
			System.out.printf("+------------+-------------+--------------+--------------+--------------+%n");
		
			System.out.print("\nSearch successfully! Do you want to another search? (Y/N) : ");
			char searchMore = input.next().charAt(0);
			
			if (searchMore == 'N' || searchMore == 'n') {
				searchSupp = false;
				clearConsole();
				stockManagement(searchMore);
			} else if (searchMore == 'Y' || searchMore == 'y') {
				clearConsole();
				System.out.printf("+-----------------------------------------------------------------------+%n");
				System.out.printf("\033[1m|\t\t\tSEARCH ITEMS SUPPLIER WISE\t\t\t|\033[0m%n");
				System.out.printf("+-----------------------------------------------------------------------+%n");
				continue;
			} else {
				throw new RuntimeException("You can only use 'Y' or 'N'");
			}
		}
	}
	
	public static void addItem(int choice) {
		boolean itemAdded = false;
		System.out.printf("+-----------------------------------------------------------------------+%n");
		System.out.printf("\033[1m|\t\t\t\tADD ITEM\t\t\t\t|\033[0m%n");
		System.out.printf("+-----------------------------------------------------------------------+%n");
		
		while (itemCategories[0] == null) {
			Scanner input = new Scanner(System.in);
			System.out.println("\nOOPS! It seems that you don't have any item categories in the system.");
			System.out.print("\nDo you want to add a new item category?(Y/N) : ");
			char addMore = input.next().charAt(0);

			if (addMore == 'N' || addMore == 'n') {
				addingCategory = true;
				clearConsole();
				homePage();
			} else if (addMore == 'Y' || addMore == 'y') {
				addingCategory = false;
				clearConsole();
				addItemCategory(addMore);
			} else {
				throw new RuntimeException("You can only use 'Y' or 'N'");
			}
		}
		
		while (suppliers[0][0] == null) {
			Scanner input = new Scanner(System.in);		
			System.out.println("\nOOPS! It seems that you don't have any suppliers in the system.");
			System.out.print("\nDo you want to add a new supplier?(Y/N) : ");
			char addSup = input.next().charAt(0);
			
			if (addSup == 'Y' || addSup == 'y') {
				clearConsole();
				addSupplier(addSup);
			} else if (addSup == 'N' || addSup == 'n') {
				clearConsole();
				homePage();
			} else {
				throw new RuntimeException("You can only use 'Y' or 'N'");
			}
		}
		
		boolean isContinue = true;	
		while (isContinue){
			exists = false;
			Scanner input = new Scanner(System.in);
			System.out.print("\nItem Code : ");
			String itemCode = input.nextLine();
			
			for (int i = 0; i < items.length; i++) {
				if (items[i][0] != null && items[i][0].equals(itemCode)) {
					exists = true;
					break;
				}
			}
			if (exists) {
				System.out.println("Already added. Try another item code!");
				continue;
			}
			
			System.out.println("\nSuppliers list : ");
			System.out.printf("+------------+------------------+----------------------+%n");
			System.out.printf("| %-10s | %-16s | %-20s |%n", "#", "SUPPLIER ID", "SUPPLIER NAME");
			System.out.printf("+------------+------------------+----------------------+%n");

			for (int i = 0; i < suppliers.length; i++) {
				if (suppliers[i][0] != null) {
					System.out.printf("| %-10s | %-16s | %-20s |%n", (i+1), suppliers[i][0], suppliers[i][1]);
				}
			}
			System.out.printf("+------------+------------------+----------------------+%n");
			
			System.out.print("\nEnter the supplier number > ");
			String supplierNum = input.nextLine();
			System.out.println("\nItem categories : ");
			System.out.printf("+------------+----------------------+%n");
			System.out.printf("| %-10s | %-20s |%n", "#", "CATEGORY NAME");
			System.out.printf("+------------+----------------------+%n");

			for (int i = 0; i < itemCategories.length; i++) {
				if (itemCategories[i] != null) {
					System.out.printf("| %-10s | %-20s |%n", (i+1), itemCategories[i]);
				}
			}
			System.out.printf("+------------+----------------------+%n");
			
			System.out.print("\nEnter the category number > ");
			String categoryNum = input.nextLine();
			
			System.out.print("\nDescription : ");
			String desc = input.nextLine();
			System.out.print("Unit price  : ");
			String unitPrice = input.nextLine();
			System.out.print("Qty on hand : ");
			String qtyOnHand = input.nextLine();
			
			for (int i = 0; i < items.length; i++) {
				if (items[i][0] == null) {
					items[i][0] = itemCode;
					items[i][1] = desc;
					items[i][2] = qtyOnHand;
					items[i][3] = unitPrice;
					items[i][4] = categoryNum;
					items[i][5] = suppliers[Integer.parseInt(supplierNum)-1][0];
					itemAdded = true;
					break;
				}
			}
			if (itemAdded) {
				System.out.print("\nAdded successfully! Do you want to add another Item? (Y/N) : ");
				char addMoreItem = input.next().charAt(0);

				if (addMoreItem == 'N' || addMoreItem == 'n') {
					isContinue = false;
					clearConsole();
					stockManagement(addMoreItem);
				} else if (addMoreItem == 'Y' || addMoreItem == 'y') {
					isContinue = true;
					clearConsole();
					System.out.printf("+-----------------------------------------------------------------------+%n");
					System.out.printf("\033[1m|\t\t\t\tADD ITEM\t\t\t\t|\033[0m%n");
					System.out.printf("+-----------------------------------------------------------------------+%n");
					continue;
				} else {
					throw new RuntimeException("You can only use 'Y' or 'N'");
				}
			}
		}		
	}
	
	public static void updateItemCategory(int selection) {
		Scanner input = new Scanner(System.in);
		System.out.printf("+---------------------------------------------------------------+%n");
		System.out.printf("\033[1m|\t\t\tUPDATE ITEM CATEGORY\t\t\t|\033[0m%n");
		System.out.printf("+---------------------------------------------------------------+%n");
		boolean updatingItemCategory = false;
		
		while (!updatingItemCategory) {
			System.out.print("\nEnter the item category : ");
			String itemCategory = input.nextLine();
			
			int itemCategoryIndex = -1;			
			for (int i = 0; i < itemCategories.length; i++) {
				if (itemCategories[i]!= null && itemCategories[i].equals(itemCategory)) {
					exists = true;
					itemCategoryIndex = i;
					break;
				}
			}
			if (!exists) {
				System.out.println("Can't find category name. Try another item category!");
				continue;
			}
			System.out.print("Item category : " + itemCategories[itemCategoryIndex]);
			System.out.print("\nEnter the new Item Category : ");
			String newItemCategory = input.nextLine();
			itemCategories[itemCategoryIndex] = newItemCategory;
			
			System.out.print("Updated successfully! Do you want to update another? (Y/N) : ");
			char updateMore = input.next().charAt(0);
			
			if(updateMore  == 'Y' || updateMore  == 'y') {
				clearConsole();
				System.out.printf("+---------------------------------------------------------------+%n");
				System.out.printf("\033[1m|\t\t\tUPDATE ITEM CATEGORY\t\t\t|\033[0m%n");
				System.out.printf("+---------------------------------------------------------------+%n");
				continue;
			} else if (updateMore == 'N' || updateMore == 'n') {
				updatingItemCategory = false;
				clearConsole();
				manageItemCategory(selection);
			} else {
				throw new RuntimeException("You can only use 'Y' or 'N'");
			}
		}
	}
	
	public static void deleteItemCategory(int selection) {
		Scanner input = new Scanner(System.in);
		System.out.printf("+---------------------------------------------------------------+%n");
		System.out.printf("\033[1m|\t\t\tDELETE ITEM CATEGORY\t\t\t|\033[0m%n");
		System.out.printf("+---------------------------------------------------------------+%n");
		boolean deletingItemCategory = false;
		
		while (!deletingItemCategory) {
			System.out.print("\nEnter the item category : ");
			String itemCategory = input.nextLine();
			
			int itemCategoryIndex = -1;			
			for (int i = 0; i < itemCategories.length; i++) {
				if (itemCategories[i] != null && itemCategories[i].equals(itemCategory)) {
					exists = true;
					itemCategoryIndex = i;
					break;
				}
			}
			if (!exists) {
				System.out.println("Can't find category name. Try another item category!");
				continue;
			}
			itemCategories[itemCategoryIndex] = null;
			
			System.out.print("Deleted successfully! Do you want to delete another? (Y/N) : ");
			char deleteMore = input.next().charAt(0);
			
			if (deleteMore  == 'Y' || deleteMore  == 'y') {
				clearConsole();
				System.out.printf("+---------------------------------------------------------------+%n");
				System.out.printf("\033[1m|\t\t\tDELETE ITEM CATEGORY\t\t\t|\033[0m%n");
				System.out.printf("+---------------------------------------------------------------+%n");
				continue;
			} else if (deleteMore == 'N' || deleteMore == 'n') {
				deletingItemCategory = false;
				clearConsole();
				manageItemCategory(selection);
			} else {
				throw new RuntimeException("You can only use 'Y' or 'N'");
			}
		}
	}
	
	public static void addItemCategory(int selection) {
		System.out.printf("+---------------------------------------------------------------+%n");
		System.out.printf("\033[1m|\t\t\tADD ITEM CATEGORY\t\t\t|\033[0m%n");
		System.out.printf("+---------------------------------------------------------------+%n");
		
		while (!addingCategory) {
			exists = false;
			Scanner input = new Scanner(System.in);
			boolean categoryAdded = false;
			System.out.print("\nEnter the new item category : ");
			String itemCategory = input.nextLine();
			
			for (int i = 0; i < itemCategories.length; i++) {
				if (itemCategories[i] != null && itemCategories[i].equals(itemCategory)) {
					exists = true;
					break;
				}
			}
			if (exists) {
				System.out.println("Already exists. Try another item category!");
				continue;
			}
			for (int i = 0; i < itemCategories.length; i++) {
				if (itemCategories[i] == null) {
					itemCategories[i]= itemCategory;
					categoryAdded = true;
					break;
				}
			}
			if (categoryAdded) {
				System.out.print("Added successfully! Do you want to add another category?(Y/N) : ");
				char addMore = input.next().charAt(0);
				
				if (addMore == 'N' || addMore == 'n') {
					addingCategory = false;
					clearConsole();
					manageItemCategory(selection);
				} else if (addMore == 'Y' || addMore == 'y') {
					clearConsole();
					System.out.printf("+---------------------------------------------------------------+%n");
					System.out.printf("\033[1m|\t\t\tADD ITEM CATEGORY\t\t\t|\033[0m%n");
					System.out.printf("+---------------------------------------------------------------+%n");
					continue;
				} else {
					throw new RuntimeException("You can only use 'Y' or 'N'");
				}
			} else {
				throw new RuntimeException("Category list is full. Cannot add more categories.");
			}
		}
	}
	
	public static void manageItemCategory(int choice) {
		Scanner input = new Scanner(System.in);
		System.out.printf("+-----------------------------------------------------------------------+%n");
		System.out.printf("\033[1m|\t\t\t  MANAGE ITEM CATEGORY \t\t\t\t|\033[0m%n");
		System.out.printf("+-----------------------------------------------------------------------+%n");
		System.out.print("\n[1] Add New Item Category\t\t");
		System.out.println("[2] Delete Item Category");
		System.out.print("[3] Update Item Category\t\t");
		System.out.println("[4] Stock Management");
		
		System.out.print("\nEnter an option to continue > ");
		int selection = input.nextInt();
		
		switch (selection) {
			case 1:
				clearConsole();
				addItemCategory(selection);
				break;
			case 2:
				clearConsole();
				deleteItemCategory(selection);
				break;
			case 3:
				clearConsole();
				updateItemCategory(selection);
				break;
			case 4:
				clearConsole();
				stockManagement(selection);
				break;
			default:
				throw new RuntimeException("You can only use 1, 2, 3 or 4");
		}
	}
	
	public static void stockManagement(int option) {
		Scanner input = new Scanner(System.in);
		System.out.printf("+-----------------------------------------------------------------------+%n");
		System.out.printf("\033[1m|\t\t\t\tSTOCK MANAGEMENT\t\t\t|\033[0m%n");
		System.out.printf("+-----------------------------------------------------------------------+%n");
		System.out.print("\n[1] Manage Item Categories\t\t\t");
		System.out.println("[2] Add item");
		System.out.print("[3] Get Items Supplier Wise\t\t\t");
		System.out.println("[4] View Items");
		System.out.print("[5] Rank Items Per unit Price\t\t\t");
		System.out.println("[6] Home Page");
		
		System.out.print("\nEnter an option to continue > ");
		int choice = input.nextInt();
		
		switch (choice) {
			case 1:
				clearConsole();
				manageItemCategory(choice);
				break;
			case 2:
				clearConsole();
				addItem(choice);
				break;
			case 3:
				clearConsole();
				getItemsSupplierWise(choice);
				break;
			case 4:
				clearConsole();
				viewItems(choice);
				break;
			case 5:
				clearConsole();
				rankItemsPerUnitPrice(choice);
				break;
			case 6:
				clearConsole();
				homePage();
				break;
			default:
				throw new RuntimeException("You can only use 1, 2, 3, 4, 5 or 6");
		}
	}
	
	public static void searchSupplier(int election) {		
		System.out.printf("+-----------------------------------------------------------------------+%n");
		System.out.printf("\033[1m|\t\t\t\tSEARCH SUPPLIER\t\t\t\t|\033[0m%n");
		System.out.printf("+-----------------------------------------------------------------------+%n");		
		boolean validSupplier = false;
		
		while (!validSupplier) {
			exists = false;
			Scanner input = new Scanner(System.in);
			System.out.print("\nSupplier ID   : ");
			String supplierId = input.nextLine();
			
			int supplierIndex = -1;		
			for (int i = 0; i < suppliers.length; i++) {
				if (suppliers[i][0] != null && suppliers[i][0].equals(supplierId)) {
					supplierIndex = i;
					System.out.print("Supplier Name : " + suppliers[supplierIndex][1]);
					exists = true;
					break;
				}
			}
			if (!exists) {
				System.out.println("Can't find supplier id. Try again!");
				continue;
			}
			System.out.print("\nSearch successfully! Do you want to another search? (Y/N) : ");
			char searchMore = input.next().charAt(0);
			
			if (searchMore  == 'N' || searchMore  == 'n') {
				validSupplier = false;
				clearConsole();
				supplierManage(election);
			} else if (searchMore  == 'Y' || searchMore  == 'y') {
				clearConsole();
				System.out.printf("+-----------------------------------------------------------------------+%n");
				System.out.printf("\033[1m|\t\t\t\tSEARCH SUPPLIER\t\t\t\t|\033[0m%n");
				System.out.printf("+-----------------------------------------------------------------------+%n");
				continue;
			} else {
				throw new RuntimeException("You can only use 'Y' or 'N'");
			}
		}
	}
	
	public static void viewSuppliers(int election) {
		Scanner input = new Scanner(System.in);
		System.out.printf("+-----------------------------------------------------------------------+%n");
		System.out.printf("\033[1m|\t\t\t\tVIEW SUPPLIERS\t\t\t\t|\033[0m%n");
		System.out.printf("+-----------------------------------------------------------------------+%n");
		System.out.println("");
		System.out.printf("+------------------+----------------------+%n");
		System.out.printf("| %-16s | %-20s |%n", "SUPPLIER ID", "SUPPLIER NAME");
		System.out.printf("+------------------+----------------------+%n");

		for (int i = 0; i < suppliers.length; i++) {
			if (suppliers[i][0] != null) {
				System.out.printf("| %-16s | %-20s |%n", suppliers[i][0], suppliers[i][1]);
			}
		}
		System.out.printf("+------------------+----------------------+%n");
		System.out.print("\nDo you want to go supplier manage page(Y/N)? : ");
		char goBack = input.next().charAt(0);
			
		if(goBack  == 'Y' || goBack  == 'y') {
			clearConsole();
			supplierManage(election);
		} else if (goBack == 'N' || goBack == 'n') {
			clearConsole();
			homePage();
		} else {
			throw new RuntimeException("You can only use 'Y' or 'N'");
		}
	}
	
	public static void deleteSupplier(int election) {
		Scanner input = new Scanner(System.in);
		System.out.printf("+-----------------------------------------------------------------------+%n");
		System.out.printf("\033[1m|\t\t\t\tDELETE SUPPLIER\t\t\t\t|\033[0m%n");
		System.out.printf("+-----------------------------------------------------------------------+%n");
		boolean deletingSupplier = false;
		
		while (!deletingSupplier) {
			exists = false;
			System.out.print("\nSupplier ID   : ");
			String supplierId = input.nextLine();
			
			int supplierIndex = -1;			
			for (int i = 0; i < suppliers.length; i++) {
				if (suppliers[i][0] != null && suppliers[i][0].equals(supplierId)) {
					exists = true;
					supplierIndex = i;
					break;
				}
			}
			if (!exists) {
				System.out.println("Can't find supplier id. Try again!");
				continue;
			}
			suppliers[supplierIndex][0] = null;
			suppliers[supplierIndex][1] = null;
			
			System.out.print("Deleted successfully! Do you want to delete another? (Y/N) : ");
			char deleteMore = input.next().charAt(0);
			
			if (deleteMore  == 'N' || deleteMore  == 'n') {
				deletingSupplier = false;
				clearConsole();
				supplierManage(election);
			} else if (deleteMore  == 'Y' || deleteMore  == 'y') {
				clearConsole();
				System.out.printf("+-----------------------------------------------------------------------+%n");
				System.out.printf("\033[1m|\t\t\t\tDELETE SUPPLIER\t\t\t\t|\033[0m%n");
				System.out.printf("+-----------------------------------------------------------------------+%n");
				continue;
			} else {
				throw new RuntimeException("You can only use 'Y' or 'N'");
			}
		}
	}
	
	public static void updateSupplier(int election) {
		Scanner input = new Scanner(System.in);
		System.out.printf("+-----------------------------------------------------------------------+%n");
		System.out.printf("\033[1m|\t\t\t\tUPDATE SUPPLIER\t\t\t\t|\033[0m%n");
		System.out.printf("+-----------------------------------------------------------------------+%n");
		boolean updatingSupplier = false;
		
		while (!updatingSupplier) {
			System.out.print("\nSupplier ID   : ");
			String supplierId = input.nextLine();
			
			int supplierIndex = -1;			
			for (int i = 0; i < suppliers.length; i++) {
				if (suppliers[i][0] != null && suppliers[i][0].equals(supplierId)) {
					exists = true;
					supplierIndex = i;
					break;
				}
			}
			if (!exists) {
				System.out.println("Can't find supplier id. Try again!");
				continue;
			}
			System.out.print("Supplier Name : " + suppliers[supplierIndex][1]);
			System.out.print("\nEnter the new supplier name : ");
			String newSupplierName = input.nextLine();
			suppliers[supplierIndex][1] = newSupplierName;
			
			System.out.print("Updated successfully! Do you want to update another supplier? (Y/N) : ");
			char updateMore = input.next().charAt(0);
			
			if (updateMore  == 'Y' || updateMore  == 'y') {
				clearConsole();
				System.out.printf("+-----------------------------------------------------------------------+%n");
				System.out.printf("\033[1m|\t\t\t\tUPDATE SUPPLIER\t\t\t\t|\033[0m%n");
				System.out.printf("+-----------------------------------------------------------------------+%n");
				continue;
			} else if (updateMore  == 'N' || updateMore  == 'n') {
				updatingSupplier = false;
				clearConsole();
				supplierManage(updateMore);
			} else {
				throw new RuntimeException("You can only use 'Y' or 'N'");
			}
		}
	}
	
	public static void addSupplier(int election) {		
		System.out.printf("+-----------------------------------------------------------------------+%n");
		System.out.printf("\033[1m|\t\t\t\tADD SUPPLIER\t\t\t\t|\033[0m%n");
		System.out.printf("+-----------------------------------------------------------------------+%n");
		boolean addingSupplier = false;
		
		while (!addingSupplier) {
			exists = false;
			Scanner input = new Scanner(System.in);
			boolean supplierAdded = false;
			System.out.print("\nSupplier ID   : ");
			String supplierId = input.nextLine();
			
			for (int i = 0; i < suppliers.length; i++) {
				if (suppliers[i][0] != null && suppliers[i][0].equals(supplierId)) {
					exists = true;
					break;
				}
			}
			if (exists) {
				System.out.println("Already exists. Try another supplier id!");
				continue;
			}
			System.out.print("Supplier Name : ");
			String supplierName = input.nextLine();
			
			for (int i = 0; i < suppliers.length; i++) {
				if (suppliers[i][0] == null) {
					suppliers[i][0] = supplierId;
					suppliers[i][1] = supplierName;
					supplierAdded = true;
					break;
				}
			}
			if (supplierAdded) {
				System.out.print("Added successfully! Do you want to add another supplier?(Y/N) :");
				char addMore = input.next().charAt(0);
				
				if (addMore == 'Y' || addMore == 'y') {
					clearConsole();
					System.out.printf("+-----------------------------------------------------------------------+%n");
					System.out.printf("\033[1m|\t\t\t\tADD SUPPLIER\t\t\t\t|\033[0m%n");
					System.out.printf("+-----------------------------------------------------------------------+%n");
					continue;									
				} else if (addMore == 'N' || addMore == 'n') {
					addingSupplier = false;
					clearConsole();
					supplierManage(election);
				} else {
					throw new RuntimeException("You can only use 'Y' or 'N'");
				}
			} else {
				throw new RuntimeException("Supplier list is full. Cannot add more suppliers.");
			}
		}
	}
	
	public static void supplierManage(int option) {
		Scanner input = new Scanner(System.in);		
		System.out.printf("+-----------------------------------------------------------------------+%n");
		System.out.printf("\033[1m|\t\t\t\tSUPPLIER MANAGE\t\t\t\t|\033[0m%n");
		System.out.printf("+-----------------------------------------------------------------------+%n");
		System.out.print("\n[1] Add Supplier\t\t\t");
		System.out.println("[2] Update Supplier");
		System.out.print("[3] Delete Supplier\t\t\t");
		System.out.println("[4] View Suppliers");
		System.out.print("[5] Search Supplier\t\t\t");
		System.out.println("[6] Home Page");
		
		System.out.print("\nEnter an option to continue > ");
		int election = input.nextInt();
		
		switch (election) {
			case 1:
				clearConsole();
				addSupplier(election);
				break;
			case 2:
				clearConsole();
				updateSupplier(election);
				break;
			case 3:
				clearConsole();
				deleteSupplier(election);
				break;
			case 4:
				clearConsole();
				viewSuppliers(election);
				break;
			case 5:
				clearConsole();
				searchSupplier(election);
				break;
			case 6:
				clearConsole();
				homePage();
				break;
			default:
				throw new RuntimeException("You can only use 1, 2, 3, 4, 5 or 6");
		}
	}
	
	public static void credentialManage(int option) {
		Scanner input = new Scanner(System.in);
		String userName = "";
		String password = "";		
		System.out.printf("+-----------------------------------------------------------------------+%n");
		System.out.printf("\033[1m|\t\t\tCREDENTIAL MANAGE\t\t\t\t|\033[0m%n");
		System.out.printf("+-----------------------------------------------------------------------+%n");
		
		boolean isChanged = false;
		while (!isChanged) {	
			while (!uName.equals(userName)) {
				System.out.print("\nPlease enter the user name to verify it's you : ");
				userName = input.nextLine();			
				if(uName.equals(userName)) {
					break;
				}
				System.out.println("invalid user name. try again!");				
			} 
			System.out.println("Hey " + uName);
							
			while (!password.equals(psswrd)) {
				System.out.print("\nEnter your current password : ");
				password = input.nextLine();			
				if (password.equals(psswrd)) {
					break;
				}
				System.out.println("incorrect password. try again!");
			}
			System.out.print("\nEnter your new password : ");
			String newPswd = input.nextLine();
			
			System.out.print("Password changed successfully! Do you want to go home page (Y/N) : ");
			char goBack = input.next().charAt(0);
			
			psswrd = newPswd;
			
			if (goBack == 'Y' || goBack == 'y') {
				isChanged = false;
				clearConsole();
				homePage();
			} else if (goBack == 'N' || goBack == 'n') {
				clearConsole();
				System.out.printf("+-----------------------------------------------------------------------+%n");
				System.out.printf("\033[1m|\t\t\tCREDENTIAL MANAGE\t\t\t\t|\033[0m%n");
				System.out.printf("+-----------------------------------------------------------------------+%n");
				continue;
			} else {
				throw new RuntimeException("You can only use 'Y' or 'N'");
			}
		}
	}
	
	public static void homePage() {
		boolean managementSystem = true;
		Scanner input = new Scanner(System.in);
		System.out.printf("+-----------------------------------------------------------------------+%n");
		System.out.printf("\033[1m|\t\tWELCOME TO IJSE STOCK MANAGEMENT SYSTEM\t\t\t|\033[0m%n");
		System.out.printf("+-----------------------------------------------------------------------+%n");		
		System.out.print("\n[1] Change the Credentials\t\t");
		System.out.println("[2] Supplier Manage");
		System.out.print("[3] Stock Manage\t\t\t");
		System.out.println("[4] Log out");
		System.out.println("[5] Exit the system");
		
		System.out.print("\nEnter an option to continue > ");
		int option = input.nextInt();
			
		switch (option) {
			case 1: 
				clearConsole();
				credentialManage(option);
				break;
			case 2:
				clearConsole();
				supplierManage(option);
				break;
			case 3:
				clearConsole();
				stockManagement(option);
				break;
			case 4:
				loginSuccessful = false;
				userNameCorrect = false;
				clearConsole();
				loginToSystem();
				clearConsole();
				homePage();
				break;
			case 5:
				clearConsole();
				managementSystem = false;
				break;
			default:
				throw new RuntimeException("You can only use 1, 2, 3, 4 or 5");
		} 
	}
	
	private final static void clearConsole() {
		final String os = System.getProperty("os.name");
		try {
			if (os.equals("Linux")) {
				System.out.print("\033\143");
			} else if (os.equals("Windows")) {
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			} else {
				System.out.print("\033[H\033[2J");
				System.out.flush();
			}
		} catch (final Exception e) {
			//handle the exception
			System.err.println(e.getMessage());
		}
	}

	public static void loginToSystem() {
		Scanner input = new Scanner(System.in);
		System.out.printf("+-----------------------------------------------------------------------+%n");
		System.out.printf("\033[1m|\t\t\t\tLOGIN PAGE\t\t\t\t|\033[0m%n");
		System.out.printf("+-----------------------------------------------------------------------+%n");	
		
		while (!userNameCorrect) {
			System.out.print("\nUser Name : ");
			String userName = input.nextLine();
		
			if (!userName.equals(uName)) {
				System.out.println("User name is invalid. Please try again!");
			} else {
				userNameCorrect = true;
			}
		}
			
		while (!loginSuccessful) {
			System.out.print("\nPassword : ");
			String password = input.nextLine();
			
			if (!password.equals(psswrd)) {
				System.out.println("Password is incorrect. Please try again!");
			} else {
				loginSuccessful = true;
			}
		}
	}
	
	public static void main(String args[]) {
		loginToSystem();	
		clearConsole();
		homePage();	
	}
}
