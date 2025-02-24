package org.techhub.ClientApp;

import java.time.LocalDate;

import java.util.*;
import org.apache.log4j.*;
import org.techhub.Model.*;
import org.techhub.Service.*;
import org.techhub.Verification.EmailVerification;

public class RestaurantOrderManagementSystem {
	private static Logger logger = Logger.getLogger(RestaurantOrderManagementSystem.class);
	public static int menucnt = 0;
	static {
		logger.info("Initializing Log4j");
		PropertyConfigurator.configure(
				"C:\\Users\\15s-eq0024au\\Techhubworkspace\\RestaurantOrderManagementSystem\\src\\main\\resources\\log4j.properties");
	}
	static boolean isLoggedIn = false;
	public static String emailId;
	public static int tableNo1 = 0;
	private static String storedEmail;
	private static int verificationCode;
	public static List<Integer> orderIds = new ArrayList<>();
	public static double newUserDis=0;
	public static double oldCustDis=0;

	public static void main(String[] args) throws InterruptedException {
		logger.debug(" Main Method Started..");
		Scanner sc = new Scanner(System.in);
		CustomerService custService = new CustomerServiceImp();
		StaffService staffService = new StaffServiceImp();
		MenuService menuService = new MenuServiceImp();
		DishService dishService = new DishServiceImp();
		TableService tableService = new TableServiceImp();
		OrderService orderService = new OrderServiceImp();

		List<OrderModel> orderList = new ArrayList<OrderModel>();

		System.err.println("|~~~~~~~~~🍜~~~~~~~~~~~🍜~~~~~~~~~~🍜~~~~~~~~~🍜~~~~~~~~~~~~🍜~~~~~~~~~~~~|");
		System.err.println("🍷                                                                        🍷");
		System.err.println("🍷                 🌹🍽️🌹|WELCOME TO THE KR's Delights|🌹🍽️🌹             🍷");
		System.err.println("🍷                                                                        🍷");
		System.err.println("|~~~~~~~~🍜~~~~~~~~~~~🍜~~~~~~~~~~🍜~~~~~~~~~🍜~~~~~~~~~~~~🍜~~~~~~~~~~~~~|");

		Thread.sleep(1000);
		System.out.println("\nLogin To the System");
		String userEmail1 = "";
		String password1 = "";
		int attempts = 0;
		while (!isLoggedIn) {

			Thread.sleep(1000);
			System.err.println("1:LOGIN");
			System.err.println("2:REGISTER");
			System.out.println("---------------------------------------------------------");
			System.out.print("Enter Choice:");
			switch (sc.nextInt()) {

			case 1:
				sc.nextLine();
				System.out.println("****************************LOGIN*****************************");
				System.out.println("\nLogin To the System");
				System.out.print("Enter Email:");
				String userEmail = sc.nextLine();
				userEmail1 = userEmail;
				emailId = userEmail;
				System.out.print("Enter PassWord:");
				String password = sc.nextLine();
				password1 = password;
				if (login(staffService, userEmail, password)) {
					logger.info("Logged In Successfully");
					isLoggedIn = true;
					attempts = 3;
				} else {
					logger.info("Login Failed !!!!");
					attempts++;
					System.out.println("Invalid email or password. Attempts remaining: " + (3 - attempts));
					isLoggedIn = false;
					if (attempts == 3 && isLoggedIn == false) {
						System.out.println("Please Try Again After Some time !!!");
						logger.info("Login Failed !!!!");
						return;
					}
					System.out.print("Forgot Password ?(Y/N) :");
					String forgotPass = sc.nextLine();
					if (!forgotPass.equalsIgnoreCase("N")) {
						System.out.print("Enter Email:");
						String mail = sc.nextLine();
						System.out.println("Verify Email to Proceed...");
						verificationCode = EmailVerification.generateVerificationCode();
						EmailVerification.sendVerificationEmail(storedEmail, verificationCode);
						System.out.println("Enter Verification Code:");
						int verifyCode = sc.nextInt();
						if (verificationCode == verifyCode) {
							System.out.print("Please enter the code:");
							System.out.print("Enter New Password:");
							String pass = sc.nextLine();
							System.out.print("Confirm Password:");
							String confirmPass = sc.nextLine();
							if (pass.equals(confirmPass)) {
								if (staffService.forgotPassword(mail, pass)) {
									System.out.println("Password Changed Successfully...");
								} else {
									System.out.println("Password Not Changed !!");
								}
							} else {
								System.out.println("PassWord and Confirm password is Different !!!");
							}
						} else {
							System.out.println("Wrong verification code entered !!!");
						}
					}
				}
				
				break;
			case 2:
				System.out.println("Register");
				System.out.println("1:Customer Registration");
				System.out.println("2:Staff Registration");
				System.out.print("\nEnter Choice:");
				switch (sc.nextInt()) {
				case 1:
					if (registerCustomer(custService, sc)) {
						System.out.println("Please login to proceed...");
						while (true) {
							System.out.print("Enter Email:");
							userEmail = sc.nextLine();
							userEmail1 = userEmail;
							emailId = userEmail;
							System.out.print("Enter Password:");
							password = sc.nextLine();
							password1 = password;
							if (login(staffService, userEmail, password)) {
								isLoggedIn = true;
								System.out.println("Login successful!");
								logger.info("Logged in Successfully");
								break;
							} else {
								logger.info("Login Failed !!!!\n Please Try Again");
							}
						}
					}

					break;
				case 2:
					sc.nextLine();
					System.out.println("Registering New Staff");
					System.out.print("\nEnter The Security Key:");
					String skey = sc.nextLine();
					if (skey.equals(StaffModel.securityKey)) {
						if (registerStaff(staffService, sc)) {
							System.out.println("Please Login to Proceed..");
							int loginAttempts = 0;
							while (loginAttempts < 3) {
								System.out.print("Enter Email:");
								userEmail = sc.nextLine();
								userEmail1 = userEmail;
								emailId = userEmail;
								System.out.print("Enter Password:");
								password = sc.nextLine();
								password1 = password;
								if (login(staffService, userEmail, password)) {
									isLoggedIn = true;
									System.out.println("Login successful!");
									logger.info("Logged In Successfully");
									break;
								} else {
									loginAttempts++;
									System.out.println(
											"Invalid email or password. Attempts remaining: " + (3 - loginAttempts));
								}
							}
							if (loginAttempts > 3) {
								System.out.println("Maximum login attempts exceeded. Please try again later.");
								logger.info("Login Failed !!!!");
								isLoggedIn = false;
								return;
							}
						}
					}
					break;
				default:
					System.out.println("Invalid Choice!!!");
				}
				break;
			}
		}
		if (attempts > 3) {
			System.out.println("Maximum attempts exceeded. Please try again later.");
			logger.info("Maximum attempts for Login Exceeded!!!");
		}

		if (staffService.validateUser(userEmail1, password1).equalsIgnoreCase("Customer")
				|| staffService.validateUser(userEmail1, password1).equalsIgnoreCase("Waiter")) {
			logger.info("User Validated Successfully.....");
			do {
				System.out.println("\n**********************CUSTOMER MENU**************************");
				System.out.println("0:LOGOUT");
				System.out.println("1:VIEW UNOCCUPIED TABLES");
				System.out.println("2:VIEW MAIN MENU");
				System.out.println("3:VIEW MENU WITH TYPE");
				System.out.println("4:PLACE ORDER");
				System.out.println("5:VIEW BILL");
				System.out.println("6:COMPLETE PAYMENT");
				System.out.println("7:RATE US");
				System.out.println("***************************************************************\n");

				System.out.print("\nEnter Choice:");

				switch (sc.nextInt()) {

				case 0:
					logger.info("Exiting .....");
					System.err.println(
							"\n------------------------------You Have Successfully Logged Out-------------------");
					System.exit(0);
				case 1:
					logger.info("Displaying Unoccupied Tables");
					System.out.println("_______________________________________________________________________");
					System.out.println("Display All UnOccupied Tables....");
					if (!tableService.showAvailableTables()) {
						System.out.println("No Tables Are Available");
					}
					break;
				case 2:
					logger.info("Displaying Food Menu...");
					System.out.println("_______________________________________________________________________");
					System.out.println("View Main Menu...");
					showFoodMenu(menuService);
					break;
				case 3:
					logger.info("Display Dishes Under Specific Menu Type...");
					System.out.println("_______________________________________________________________________");
					showFoodMenu(menuService);
					sc.nextLine();
					showDishes(menuService, dishService, sc);
					break;
				case 4:
					logger.info("Reserving Table And Placing The Order...");
					sc.nextLine();
					orderList.clear();
					System.out.println("___________________________________________________________________________");
					if (tableService.showAvailableTables()) {
						System.out.print("\nEnter Table No: ");
						int tableNo = sc.nextInt();
						tableNo1 = tableNo;
						sc.nextLine();
						if (tableService.reserveTable(tableNo, emailId)) {
							System.out.println("Table " + tableNo + " reserved for customer..");
							boolean orderFlag = true;
							while (orderFlag == true) {
								showFoodMenu(menuService);
								if (showDishes(menuService, dishService, sc)) {
									OrderModel order = placeOrder(sc, dishService, tableNo);
									orderList.add(order);
								} else {
									System.out.println("Cannot Add Item from Given Menu !!");
								}
								System.out.print("Want To Add More Items(Y/N): ");
								String answer = sc.nextLine();
								if (answer.equalsIgnoreCase("N")) {
									for (OrderModel orderModel : orderList) {
										orderService.orderPlaced(orderModel);
									}
									System.out.println("Order Placed Successfully...");
									logger.info("Order Placed SuccessFully....");
									orderFlag = false;
								}
							}
						} else {
							System.out.println("Cannot Reserve this Table!!!");
						}

					} else {
						System.out.println("No Table is Available for Reservation Please try again after some time !!");
						logger.info("No Table is Available For Reservation.....");
					}
					break;
				case 5:
					Thread.sleep(500);
					logger.info("Showing Bill...");
					System.err.println("New Year Special Offer...");
					System.err.println("10% Discount on Orders Above 500");
					System.err.println("20% Discount on Orders Above 1000");
					CustomerModel cmodel = custService.getCustomer(userEmail1);
					if(orderService.checkIfCustomerIsNew(userEmail1)) {
						newUserDis=0.05;
					}
					if(orderService.checkCustomerOrders(userEmail1)) {
						oldCustDis=0.05;
					}
					if (cmodel != null) {
						System.out.println("----------------------------------------------------------------------");
						System.out.println("Customer Name:" + cmodel.getCustName());
						System.out.println("Email:" + cmodel.getCustEmail());
						System.out.println("Contact:" + cmodel.getCustContact());
						System.out.println("----------------------------------------------------------------------");
						List<OrderModel> orders = orderService.ViewOrderByTableNo(tableNo1);
						generateBill(orders, orderService,newUserDis,oldCustDis);
					} else {
						System.out.println("Invalid MailId !!!!");
					}

					break;
				case 6:
					logger.info("Complete Payment Process....");
					System.out.println("___________________________________________________________________________");
					System.out.print("Enter Bill Amount:");
					double amount = sc.nextDouble();
					if (orderService.completePayment(amount, tableNo1)) {
						System.out.println("Payment is Completed..");
						System.err.println("\n\n🩷🤗🤗🩷..THANK YOU FOR CHOOSING US..VISIT AGAIN..🩷🤗🤗🩷\n");
						Thread.sleep(500);
					} else {
						System.out.println("Payment Not Completed !!! Check Amount ...");
					}
					break;
				case 7:
					String feedBack="";
					String rating="";
					System.out.print("Rate Us From 1 to 5 :");
					switch(sc.nextInt()) {
					case 1:
						feedBack="Very Bad";
						rating="*";
						break;
					case 2:
						feedBack="Bad";
						rating="**";
						break;
					case 3:
						feedBack="Good";
						rating="***";
						break;
					case 4:
						feedBack="Very Good";
						rating="****";
						break;
					case 5:
						feedBack="Excellent Service";
						rating="*****";
						break;
					}
					FeedBackModel model=new FeedBackModel(0,feedBack,rating,userEmail1);
					if(orderService.getFeedback(model)) {
						System.out.println("FeedBack Added Successfully");
						System.err.println("You Rated Us "+ rating+" out of ***** Thank You");
					}
					else {
						System.out.println("Feedback not added !!!");
					}
					break;
				default:
					System.out.println("Wrong Choice Entered !!!!");

				}

			} while (true);

		} else if (staffService.validateUser(userEmail1, password1).equalsIgnoreCase("Admin")) {
			logger.info("User Validated Successfully.....");
			do {
				System.out.println("\n**************************MAIN MENU*************************");
				System.out.println("0:LOGOUT");
				System.out.println("1:CUSTOMER");
				System.out.println("2:STAFF");
				System.out.println("3:MENU");
				System.out.println("4:DISHES");
				System.out.println("5:ORDERS");
				System.out.println("***************************************************************\n");

				System.out.print("Enter Choice:");
				int choice = sc.nextInt();
				switch (choice) {
				case 0:
					logger.info("Logging Out....");
					sc.nextLine();
					System.out.print("\nAre You Sure You want to LogOut ? (Y/N):");
					if (sc.nextLine().equalsIgnoreCase("Y")) {
						System.err.println("\n-------------------You Have sucessfully logged out-----------------");
						System.exit(0);
					}
					break;
				case 1:
					logger.info("Entering Into Customer Menu.....");
					boolean flag = true;
					do {
						System.out.println("\n***********************CUSTOMER MENU*************************");
						System.out.println("0:EXIT CUSTOMER MENU");
						System.out.println("1:ADD NEW CUSTOMER");
						System.out.println("2:VIEW ALL CUSTOMERS");
						System.out.println("3:SEARCH CUSTOMER");
						System.out.println("4:DELETE CUSTOMER");
						System.out.println("***************************************************************\n");

						System.out.print("Enter Choice:");
						switch (sc.nextInt()) {
						case 0:
							logger.info("Exiting From Customer Menu.....");
							System.out.println("Exit Customer menu !!!");
							flag = false;
							break;
						case 1:
							logger.info("Registering New Customer.....");
							registerCustomer(custService, sc);
							break;

						case 2:
							logger.info("Display All Customer Details.....");
							System.out.println("Display All Customers......");
							System.out.println("____________________________________________________________\n");
							Optional<List<CustomerModel>> o = custService.getAllCustomers();
							if (o.isPresent()) {
								List<CustomerModel> model = o.get();
								if (model == null) {
									System.out.println("Customer Master is Empty.....");
								} else {
									System.out.println(
											"-------------------------------------------------------------------------");
									System.out.printf("%-5s %-20s %-30s %-12s %-20s %-20s\n", "Id", "Name", "Email",
											"Contact", "Address", "Role");
									System.out.println(
											"-------------------------------------------------------------------------");

									model.forEach((cust) -> System.out.printf("%-5d %-20s %-30s %-12s %-20s %-20s\n",
											cust.getCustId(), cust.getCustName(), cust.getCustEmail(),
											cust.getCustContact(), cust.getAddress(), cust.getRole()));

									System.out.println(
											"-------------------------------------------------------------------------");

								}
							} else {
								System.out.println("No Data Found!!!!");
							}
							break;

						case 3:
							logger.info("Searching Customer with Email...");
							sc.nextLine();
							System.out.println("\n_______________________________________________________________");
							System.out.print("Enter Customer Email to Search :");
							String email = sc.nextLine();
							if (custService.getCustomerByEmail(email) != null) {
								System.out
										.println("Customer Found with Email:" + custService.getCustomerByEmail(email));
							} else {
								System.out.println("Customer Not Found!!!!!!!!!");
							}
							break;
						case 4:
							logger.info("Deleting Customer....");
							sc.nextLine();
							System.out.println("\n______________________________________________________________");
							System.out.print("Enter Email To Delete Customer:");
							email = sc.nextLine();
							if (custService.deleteCustomer(email)) {
								System.out.println("Customer Deleted successfully...........");
								logger.info("Customer deleted successfully....");
							} else {
								logger.info("Customer not deleted!!");
								System.out.println("Customer Not Deleted !!!!");
							}
							break;
						default:
							System.out.println("Wrong Choice Entered !!!!");
						}
					} while (flag);
					break;
				case 2:
					logger.info("Entering into staff Menu...");
					flag = true;
					do {
						System.out.println("\n*************************STAFF MENU**************************");
						System.out.println("0:EXIT STAFF MENU");
						System.out.println("1:ADD NEW STAFF");
						System.out.println("2:VIEW ALL STAFF");
						System.out.println("3:SEARCH STAFF");
						System.out.println("4:DELETE STAFF");
						System.out.println("***************************************************************\n");

						System.out.print("Enter Choice:");
						switch (sc.nextInt()) {

						case 0:
							logger.info("Exiting Staff Menu");
							System.out.println("Exiting Staff Menu !!!");
							flag = false;
							break;
						case 1:
							logger.info("Registering New Staff....");
							sc.nextLine();
							registerStaff(staffService, sc);
							break;
						case 2:
							logger.info("Displaying staff Details...");
							Thread.sleep(1000);
							System.out.println("Display All Staff Details......");
							System.out.println("____________________________________________________________\n");
							Optional<List<StaffModel>> o1 = staffService.getAllStaff();
							if (o1.isPresent()) {
								List<StaffModel> model = o1.get();
								if (model == null) {
									System.out.println("Staff Master is Empty.....");
									logger.info("No Staff Found!!!");
								} else {
									logger.info("Staff Details Found...");
									System.out
											.println("---------------------------------------------------------------");
									System.out.printf("%-5s %-30s %-30s %-10s %-20s %-20s\n", "Id", "Name", "Email",
											"Contact", "Address", "Role");
									System.out
											.println("---------------------------------------------------------------");

									model.forEach((staff) -> System.out.printf("%-5d %-30s %-30s %-10s %-20s %-20s\n",
											staff.getStaffId(), staff.getStaffName(), staff.getEmail(),
											staff.getContact(), staff.getAddress(), staff.getRole()));

									System.out
											.println("---------------------------------------------------------------");

								}
							}
							break;
						case 3:
							logger.info("Searching Staff By Email..");
							sc.nextLine();
							System.out.println("\n______________________________________________________");
							System.out.print("Enter Staff Email to Search :");
							String email = sc.nextLine();
							if (staffService.getStaffByEmail(email) != null) {
								logger.info("staff found...");
								System.out.println("staff Found with Email:" + staffService.getStaffByEmail(email));
							} else {
								logger.info("staff not found!!");
								System.out.println("Staff Not Found!!!!!!!!!");
							}
							break;
						case 4:
							logger.info("Deleting Staff...");
							sc.nextLine();
							System.out.println("__________________________________________________");
							System.out.print("Enter Email To Delete The Staff:");
							email = sc.nextLine();
							if (staffService.deleteStaff(email)) {
								logger.info("Staff Deleted...");
								System.out.println("staff Deleted successfully...........");
							} else {
								logger.info("staff not deleted!!");
								System.out.println("staff Not Deleted !!!!");
							}
							break;
						default:
							System.out.println("Wrong Choice Entered !!!!");
						}

					} while (flag);
					break;
				case 3:
					logger.info("Entering Into Food Menu...");
					flag = true;
					do {
						System.out.println("\n*************************FOOD MENU**************************");
						System.out.println("0:EXIT MENU");
						System.out.println("1:ADD MENU TYPE");
						System.out.println("2:VIEW MENU TYPE");
						System.out.println("3:SEARCH MENU TYPE");
						System.out.println("4:DELETE MENU TYPE");
						System.out.println("***************************************************************\n");

						System.out.print("Enter Choice:");
						switch (sc.nextInt()) {

						case 0:
							logger.info("Exiting Food menu...");
							System.out.println("Exiting Main Menu !!!");
							flag = false;
							break;

						case 1:
							logger.info("Adding Food Menu Type...");
							sc.nextLine();
							System.out.println("___________________________________________");
							System.out.print("Enter menuType to add:");
							String type = sc.nextLine();
							System.out.println(
									menuService.addMenuType(new MenuModel(0, type)) ? "Menu Type Added Successfully"
											: "Menu Type Not Added !!");
							break;
						case 2:
							logger.info("Displaying Food Menu..");
							showFoodMenu(menuService);
							break;
						case 3:
							logger.info("Searching Menu Type...");
							sc.nextLine();
							System.out.println("__________________________________________________");
							System.out.print("Enter menuType to search:");
							type = sc.nextLine();
							if (menuService.searchMenuType(type) != null) {
								System.out.println("MenuType " + type + " Found....");
								logger.info("Menu Type Found...");
							} else {
								logger.info("Menu Type not foun!!!");
								System.out.println("Menu Type Not Found!!!");
							}
							break;
						case 4:
							logger.info("Deleting Menu Type...");
							sc.nextLine();
							System.out.println("__________________________________________________");
							System.out.print("Enter menuType to delete:");
							type = sc.nextLine();
							if (menuService.deleteMenuType(type)) {
								logger.info("Menu Type Deleted...");
								System.out.println("Type Deleted Successfully...");
							} else {
								logger.info("Menu Type not deleted!!");
								System.out.println(" Menu Type not deleted !!");
							}
							break;
						default:
							System.out.println("Wrong Choice Entered !!!!");
						}

					} while (flag);
					break;
				case 4:
					logger.info("Entering into Dish Menu..");
					flag = true;
					do {

						System.out.println("\n****************************DISH MENU************************");
						System.out.println("0:EXIT DISH MENU");
						System.out.println("1:ADD SINGLE DISH TO MENUTYPE");
						System.out.println("2:ADD DISHES IN BULK");
						System.out.println("3:VIEW ALL DISHES");
						System.out.println("4:SEARCH A DISH BY NAME");
						System.out.println("5:DELETE DISH BY NAME");
						System.out.println("***************************************************************\n");

						System.out.print("Enter Choice:");
						switch (sc.nextInt()) {

						case 0:
							logger.info("Exiting Dish Menu!!");
							System.out.println("Exiting Dish Menu !!!");
							flag = false;
							break;

						case 1:
							sc.nextLine();
							System.out.print("Enter MenuType:");
							String menuType = sc.nextLine();
							System.out.print("Enter DishName:");
							String dishName = sc.nextLine();
							System.out.print("Enter Price:");
							int price = sc.nextInt();
							sc.nextLine();
							System.out.print("Enter Category:");
							String category = sc.nextLine();
							DishModel model = new DishModel(0, dishName, price, category);
							if (dishService.addDish(menuType, model)) {
								System.out.println("Dish Added Successfully...");
							} else {
								System.out.println("Dish Not Added!!!");
							}
							break;

						case 2:
							logger.info("Adding Bulk Dishes By MenuType..");
							System.out.println("_________________________________________________________");
							sc.nextLine();
							showFoodMenu(menuService);
							System.out.print("Enter Menu Type:");
							String type = sc.nextLine();
							if ((menuService.searchMenuType(type)) != null) {
								System.out.print("\nEnter FileName:");
								if (dishService.addBulkDishes(type, sc.nextLine())) {
									logger.info("Bulk Dishes Added in MenuType:" + type);
									System.out.println("Bulk Data Added successfully..");
								} else {
									logger.info("Bulk Dishes not Added!!");
									System.out.println("Bulk Data Not Added !!");
								}
							} else {
								logger.info("Menu Type not Found to add Dishes !!");
								System.out.println("Menu type not found !!!!");
							}
							break;
						case 3:
							logger.info("Display All Dishes Under Specific Menu...");
							System.out.println("________________________________________________________");
							sc.nextLine();
							showFoodMenu(menuService);
							showDishes(menuService, dishService, sc);
							break;
						case 4:
							logger.info("Searching Specific Dish By name...");
							System.out.println("_________________________________________________________");
							sc.nextLine();
							showFoodMenu(menuService);
							System.out.print("Enter Menu Type:");
							type = sc.nextLine();
							System.out.print("Enter DishName:");
							String name = sc.nextLine();
							if (menuService.searchMenuType(type) != null) {
								if (dishService.searchDishByName(type, name) != null) {
									logger.info("Dish Found...");
									System.out.println("Dish " + name + " Found.....");
								} else {
									logger.info("Dish Not Found in Menu :" + type);
									System.out.println("Dish Not Found!!");
								}
							} else {
								logger.info("Menu Type not Found!!");
								System.out.println("Menu Type not found !!");
							}
							break;
						case 5:
							logger.info("Deleting Single Dish By DishName..");
							System.out.println("___________________________________________");
							sc.nextLine();
							showFoodMenu(menuService);
							System.out.print("Enter Menu Type:");
							type = sc.nextLine();
							System.out.print("Enter DishName:");
							name = sc.nextLine();
							if (menuService.searchMenuType(type) != null) {
								if (dishService.deleteDishByName(type, name)) {
									System.out.println("Dish Deleted Successfully......");
									logger.info("Dish Deleted Successfully...");
								} else {
									logger.info("Dish Not Deleted !!!");
									System.out.println("Dish Not Deleted !!!!");
								}
							} else {
								logger.info("Menu Type Not Found!!");
								System.out.println("Menu Type not Found !!!");
							}
							break;
						default:
							System.out.println("Wrong Choice Entered !!!!");
						}
					} while (flag);
					break;
				case 5:
					logger.info("Entering Into Order Menu...");
					do {
						flag = true;
						System.out.println("\n***********************ORDER MENU*****************************");
						System.out.println("0:Exit");
						System.out.println("1:VIEW ALL ORDERS");
						System.out.println("2:VIEW TABLEWISE ORDERS");
						System.out.println("***************************************************************\n");

						System.out.print("Enter Choice:");
						switch (sc.nextInt()) {
						case 0:
							logger.info("Exiting Order Menu...");
							System.out.println("Exiting Order Menu !!!");
							flag = false;
							break;
						case 1:
							logger.info("Displaying all Orders...");
							System.out.println("View All Orders");
							System.out.println(
									"_________________________________________________________________________");
							Optional<List<OrderModel>> od = orderService.viewAllOrders();
							if (od.isPresent()) {
								List<OrderModel> model = od.get();
								if (model == null) {
									logger.info("No order Found!!!");
									System.out.println("Order Master is Empty.....");
								} else {
									System.out.println(
											"---------------------------------------------------------------------");
									System.out.printf("%-8s %-40s %-8s %-8s %-8s %-8s\n", "OrderId", "DishName",
											"Quantity", "Price", "TableNo", "Status");
									System.out.println(
											"---------------------------------------------------------------------");
									model.forEach((order) -> System.out.printf("%-8d %-40s %-8d %-8d %-8d %-8s\n",
											order.getOrderId(), order.getDishName(), order.getQuantity(),
											order.getPrice(), order.getTableNo(), order.getOrderStatus()));
									System.out.println(
											"---------------------------------------------------------------------");

								}
							} else {
								logger.info("No order Found!!!");
								System.out.println("No Orders Found");
							}
							break;

						case 2:
							logger.info("Displaying Order By Table No...");
							System.out.println("View Orders By Table No");
							System.out.println("______________________________________________");
							System.out.print("Enter Table No: ");
							int tableNo = sc.nextInt();
							sc.nextLine();
							List<OrderModel> model = orderService.ViewOrderByTableNo(tableNo);
							if (model.isEmpty()) {
								logger.info("No Orders found for given TableNo");
								System.out.println("No Orders Found for Table No " + tableNo);
							} else {
								System.out.println("Orders for Table No " + tableNo);
								System.out.println("---------------------------------------------------");
								System.out.printf("%-8s %-40s %-8s %-8s %-8s\n", "OrderId", "DishName", "Quantity",
										"Price", "TableNo");
								System.out.println("---------------------------------------------------");
								model.forEach((order) -> System.out.printf("%-8d %-40s %-8d %-8d %-8d\n",
										order.getOrderId(), order.getDishName(), order.getQuantity(), order.getPrice(),
										order.getTableNo()));
								System.out.println("---------------------------------------------------");

							}
							break;
						default:
							System.out.println("Invalid Choice Entered !!!");
						}

					} while (flag);
					break;
				default:
					System.out.println("Wrong Choice Entered !!!!");
				}
			} while (true);

		} else if (staffService.validateUser(userEmail1, password1).equalsIgnoreCase("Cashier")) {
			do {
				System.out.println("\n***********************MENU FOR CASHIER**********************");
				System.out.println("0:LOGOUT");
				System.out.println("1:VIEW ALL ORDERS");
				System.out.println("2:VIEW TABLEWISE ORDERS");
				System.out.println("3:GENERATE BILL");
				System.out.println("4:COMPLETE PAYMENT");
				System.out.println("5:RATE US");
				System.out.println("***************************************************************\n");

				System.out.print("Enter Choice:");
				switch (sc.nextInt()) {
				case 0:
					logger.info("Cashier Logging Out...");
					System.err.println("\n------------You Have Successuflly Logged Out---------------");
					System.exit(0);
				case 1:
					logger.info("Displaying all Orders...");
					System.out.println("View All Orders");
					System.out.println("______________________________________________________________");
					Optional<List<OrderModel>> od = orderService.viewAllOrders();
					if (od.isPresent()) {
						List<OrderModel> model = od.get();
						if (model == null) {
							logger.info("No order Found!!!");
							System.out.println("Order Master is Empty.....");
						} else {
							System.out.println("------------------------------------------------------------");
							System.out.printf("%-8s %-25s %-8s %-8s %-8s %-8s\n", "OrderId", "DishName", "Quantity",
									"Price", "TableNo", "OrderStatus");
							System.out.println("------------------------------------------------------------");
							model.forEach((order) -> System.out.printf("%-8d %-25s %-8d %-8d %-8d %-8s\n",
									order.getOrderId(), order.getDishName(), order.getQuantity(), order.getPrice(),
									order.getTableNo(), order.getOrderStatus()));
							System.out.println("------------------------------------------------------------");

						}
					} else {
						logger.info("No order Found!!!");
						System.out.println("No Orders Found");
					}
					break;

				case 2:
					logger.info("Displaying Order By Table No...");
					System.out.println("View Orders By Table No");
					System.out.println("______________________________________________________________");
					System.out.print("Enter Table No: ");
					int tableNo = sc.nextInt();
					sc.nextLine();
					List<OrderModel> model = orderService.ViewOrderByTableNo(tableNo);
					if (model.isEmpty()) {
						logger.info("No Orders found for given TableNo");
						System.out.println("No Orders Found for Table No " + tableNo);
					} else {
						System.out.println("Orders for Table No " + tableNo);
						System.out.println("------------------------------------------------------------");
						System.out.printf("%-8s %-25s %-8s %-8s %-8s\n", "OrderId", "DishName", "Quantity", "Price",
								"TableNo");
						System.out.println("------------------------------------------------------------");
						model.forEach((order) -> System.out.printf("%-8d %-25s %-8d %-8d %-8d\n", order.getOrderId(),
								order.getDishName(), order.getQuantity(), order.getPrice(), order.getTableNo()));
						System.out.println("------------------------------------------------------------");

					}
					break;
				case 3:
					logger.info("Generating Bill...");
					System.out.print("Enter TableNo:");
					tableNo = sc.nextInt();
					tableNo1 = tableNo;
					userEmail1=tableService.getEmailByTableNo(tableNo);
					if(orderService.checkIfCustomerIsNew(userEmail1)) {
						newUserDis=0.05;
					}
					if(orderService.checkCustomerOrders(userEmail1)) {
						newUserDis=0.05;
					}
					List<OrderModel> orders = orderService.ViewOrderByTableNo(tableNo);
					generateBill(orders, orderService,newUserDis,oldCustDis);
					break;
				case 4:
					logger.info("Complete Payment Process....");
					System.out.println("_________________________________________________________");
					System.out.print("Enter Bill Amount:");
					double amount = sc.nextDouble();
					if (orderService.completePayment(amount, tableNo1)) {
						System.out.println("Payment is Completed..");
						System.out.println("\n_______________________________________________________________");
					} else {
						System.out.println("Payment Not Completed !!! Check Amount ...");
					}
					break;
				case 5:
					String feedBack="";
					String rating="";
					System.out.print("Rate Us From 1 to 5 :");
					switch(sc.nextInt()) {
					case 1:
						feedBack="Very Bad";
						rating="*";
						break;
					case 2:
						feedBack="Bad";
						rating="**";
						break;
					case 3:
						feedBack="Good";
						rating="***";
						break;
					case 4:
						feedBack="Very Good";
						rating="****";
						break;
					case 5:
						feedBack="Excellent Service";
						rating="*****";
						break;
					}
					FeedBackModel fmodel=new FeedBackModel(0,feedBack,rating,userEmail1);
					if(orderService.getFeedback(fmodel)) {
						System.out.println("FeedBack Added Successfully");
						System.err.println("You Rated Us "+ rating+" out of ***** Thank You");
					}
					else {
						System.out.println("Feedback not added !!!");
					}
					break;
				default:
					System.out.println("Invalid Choice Entered !!!");
				}

			} while (true);

		}
	}

	private static boolean login(StaffService staffService, String email, String pass) {
		if (staffService.validateUser(email, pass).equalsIgnoreCase("Customer")) {
			logger.info("Customer Logged in Successfully...");
			System.out.println("Customer Logged In successfully....");
			return true;
		} else if (staffService.validateUser(email, pass).equalsIgnoreCase("Waiter")) {
			logger.info("Staff Logged In Successfully...");
			System.out.println("Staff Logged In Successfully.....");
			return true;
		} else if (staffService.validateUser(email, pass).equalsIgnoreCase("Cashier")) {
			logger.info("Cashier Logged In Successfully...");
			System.out.println("Cashier Logged In Successfully.....");
			return true;
		} else if (staffService.validateUser(email, pass).equalsIgnoreCase("Admin")) {
			logger.info("Admin Logged in Successfully...");
			System.out.println("Admin Logged In Successfully.....");
			return true;
		} else {
			logger.info("Login Failed!!!");
			System.out.println("Login Failed!!!! Please Enter Valid Details...");
			return false;
		}
	}

	private static boolean registerCustomer(CustomerService custService, Scanner sc) {
		sc.nextLine();
		System.out.println("\n************CUSTOMER REGISTRATION*************");
		System.out.print("Enter Customer Name:");
		String name = sc.nextLine();
		System.out.print("Enter Email id:");
		String email = sc.nextLine();
		storedEmail = email;
		System.out.println("Verify Email to Proceed...");
		verificationCode = EmailVerification.generateVerificationCode();
		EmailVerification.sendVerificationEmail(storedEmail, verificationCode);
		System.out.print("Please enter the code:");
		int inputCode = sc.nextInt();
		if ((verificationCode == inputCode)) {
			System.out.println("Email Verified Successfully....");
			sc.nextLine();
			System.out.print("Enter Contact No:");
			String contact = sc.nextLine();
			System.out.print("Enter Address:");
			String add = sc.nextLine();
			System.out.print("Enter Password:");
			String pass = sc.nextLine();
			if (custService.isRegisteredCustomer(new CustomerModel(0, name, email, contact, pass, add, "Customer"))) {
				System.out.println("Registered Successfully");
				logger.info("Registered Successfully....");
				return true;
			} else {
				System.out.println("Could not registered the customer");
				logger.info("Registration Failed!!!");
				return false;
			}
		} else {
			System.out.println("Invalid verification code.");
		}
		return false;
	}

	private static boolean registerStaff(StaffService staffService, Scanner sc) {
		System.out.println("\n*****************STAFF REGISTRATION****************");
		System.out.print("Enter Staff Name:");
		String name = sc.nextLine();
		System.out.print("Enter Email id:");
		String email = sc.nextLine();
		storedEmail = email;
		System.out.println("Verify Email to Proceed...");
		verificationCode = EmailVerification.generateVerificationCode();
		EmailVerification.sendVerificationEmail(storedEmail, verificationCode);
		System.out.print("Please enter the code:");
		int inputCode = sc.nextInt();
		// System.out.println(verificationCode);
		// System.out.println(inputCode);
		if ((verificationCode == inputCode)) {
			sc.nextLine();
			System.out.println("Email verified successfully!");
			System.out.print("Enter Contact No:");
			String contact = sc.nextLine();
			System.out.print("Enter Address:");
			String add = sc.nextLine();
			System.out.print("Enter Password:");
			String pass = sc.nextLine();
			System.out.print("Enter Role:");
			String role = sc.nextLine();
			if (staffService.isRegisteredStaff(new StaffModel(0, name, email, contact, pass, add, role))) {
				System.out.println("Staff Registered Successfully");
				logger.info("Registration Successfull...");
				return true;
			} else {
				System.out.println("Could not registered the Staff");
				logger.info("Registration Failed!!");
				return false;
			}
		} else {
			System.out.println("Invalid verification code.");
		}
		return false;
	}

	private static void showFoodMenu(MenuService menuService) {
		menucnt = 1;
		Optional<List<MenuModel>> o2 = menuService.showMenus();
		System.out.println("\n***************FOOD MENU***************\n");
		if (o2.isPresent()) {
			List<MenuModel> model = o2.get();
			if (model == null) {
				System.out.println("No Menu Items Found !!!");
			} else {
				System.out.println("SrNO.\tMenuType");
				model.forEach((m) -> System.out.println((menucnt++) + "\t" + m.getMenyType()));
			}
		} else {
			System.out.println("No Menu Item Found !!!");
		}
	}

	private static boolean showDishes(MenuService menuService, DishService dishService, Scanner sc) {
		System.out.print("Enter Menu Type:");
		String type = sc.nextLine();
		if ((menuService.searchMenuType(type)) != null) {
			List<DishModel> dishes = dishService.showAllDishes(type);
			if (!dishes.isEmpty()) {
				System.out.println("______________________________________________________________");
				System.out.printf("| %-6s | %-40s | %-6s | %-10s |\n", "DishId", "DishName", "Price", "Category");
				System.out.println("----------------------------------------------------------------");
				dishes.forEach((d) -> System.out.printf("| %-6s | %-40s | %-6s | %-10s |\n", d.getDishId(),
						d.getDishName(), d.getPrice(), d.getCategory()));
				System.out.println("----------------------------------------------------------------");
				return true;

			} else {
				System.out.println("No dishes are present in the given Menu Type.");
			}
		} else {
			System.out.println("MenuType Not Found !!!!!!!!");
		}
		return false;
	}

	private static OrderModel placeOrder(Scanner sc, DishService dishService, int tableNo) {
		System.out.print("Enter the dishId: ");
		int dishId = sc.nextInt();
		DishModel dish = dishService.getDish(dishId);
		System.out.print("Enter the quantity: ");
		int quantity = sc.nextInt();
		sc.nextLine();
		OrderModel order = new OrderModel();
		order.setDishName(dish.getDishName());
		order.setQuantity(quantity);
		order.setPrice(dish.getPrice());
		System.out.println("Price Per Item is:" + dish.getPrice());
		order.setTableNo(tableNo);
		return order;

	}

	private static void generateBill(List<OrderModel> orders, OrderService orderService,double newUserDiscount,double oldCustomerDiscount) throws InterruptedException {
	    double totalCost = 0;
	    double GST = 0;
	    double totalAmount = 0;
	    double serviceCharges = 0;
	    double discount = 0;
	    double newUserDis=0;
	    double oldCustDis=0;
	    String discountPer="";

	    for (OrderModel order : orders) {
	        totalCost += order.getPrice() * order.getQuantity();
	       // orderIds.add(order.getOrderId());
	    }

	    GST = totalCost * 0.05;
	    serviceCharges = totalCost * 0.10;
	    totalAmount = totalCost + GST + serviceCharges;
	    if (totalAmount > 1000) {
	        discount = totalAmount * 0.20;
	        discountPer="20%";
	    } else if (totalAmount > 500) {
	        discount = totalAmount * 0.10;
	        discountPer="10%";
	    }
	    totalAmount = totalCost + GST + serviceCharges - discount;
	    if(newUserDiscount>0) {
	    	newUserDis=totalAmount*0.05;
	    }
	    totalAmount=totalAmount-newUserDis;
	    if(oldCustomerDiscount>0) {
	    	oldCustDis=totalAmount*0.05;
	    }
	    totalAmount=totalAmount-oldCustDis;
	    CustomerModel model = new CustomerModel();
	    System.out.println("Bill Details:");
	    System.out.println("--------------------------------------------------------------------------");
	    System.out.printf("%-8s %-25s %-8s %-8s\n", "OrderId", "DishName", "Quantity", "Price");
	    System.out.println("--------------------------------------------------------------------------");

	    for (OrderModel order1 : orders) {
	        System.out.printf("%-8d %-25s %-8d %-8d\n", order1.getOrderId(), order1.getDishName(), order1.getQuantity(), order1.getPrice());
	    }

	    System.out.println("-------------------------------------------------------------------------");
	    System.out.println("Subtotal: " + totalCost);
	    System.out.println("GST (5%): " + GST);
	    System.out.println("Service Charges(10%):" + serviceCharges);
	    System.out.println("Discount(On Order Value): "+"("+discountPer+")" + discount);
	    if(newUserDis>0) {
	    	System.out.println("New User Discount: "+newUserDis+" (5%)");
	    }
	    if(oldCustDis>0) {
	    	System.out.println("Old Customer Discount: "+oldCustDis+" (5%)");
	    }
	    System.out.println("Total Amount: " + totalAmount);
	    System.out.println("Bill Date: " + LocalDate.now());
	    System.out.println("------------------------------------------------------------------------");
	    Thread.sleep(500);
	    if(discount>0) {
	    	System.err.println("\nCongratulations You Have Got "+discountPer+" Discount On Your Bill");
	    }

	    BillModel bill = new BillModel();
	    if (!orders.isEmpty()) {
	        bill.setOrderId(orders.get(0).getOrderId());
	        bill.setCurrentDate(LocalDate.now());
	        bill.setSubTotal(totalCost);
	        bill.setGST(GST);
	        bill.setServiceCharges(serviceCharges);
	        bill.setDiscount(discount);
	        bill.setTotal(totalAmount);
	        orderService.generateBill(bill);
	    } else {
	        System.out.println("No Order to generate the bill");
	    }
	}

}

//	logger.info("Display Dishes Under Specific Menu Type...");
//	System.out.println("_______________________________________________________________");
//	showFoodMenu(menuService);
//	showDishes(menuService, dishService, sc);
