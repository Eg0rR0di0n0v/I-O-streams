import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Scanner;

public class Main {
    private static final Product[] goods = {
            new Product("Bread", 60.0),
            new Product("Buckwheat", 92.60),
            new Product("Milk", 90.0),
            new Product("Apple", 20.0),
            new Product("Stew", 385.50),
            new Product("Condensed milk", 127.80),
            new Product("Sugar", 75.0)
    };

    public static void main(String[] args) throws FileNotFoundException, ParseException {
        Scanner sc = new Scanner(System.in);
        String s;
        Basket shoppingCart;
        int selectedItem;
        int itemCount;

        File basketFile = new File("basket.txt");

        if (basketFile.exists()) {
            System.out.println("Load the shopping cart <ENTER>? ");
            if (sc.nextLine().equals("")) {
                shoppingCart = Basket.loadFromTxtFile(basketFile);
            } else {
                shoppingCart = new Basket(goods);
            }
        } else {
            shoppingCart = new Basket(goods);
        }

        while (true) {
            shoppingCart.printGoodsList();
            s = sc.nextLine();
            String[] inputValues = s.split(" ");
            if (inputValues.length == 2) {
                try {
                    selectedItem = Integer.parseInt(inputValues[0]);
                    itemCount = Integer.parseInt(inputValues[1]);

                    if (selectedItem <= 0 || selectedItem > goods.length) {
                        System.out.print("\nIncorrect item number\n");
                        continue;
                    }
                    if (itemCount <= 0) {
                        continue;
                    }
                    shoppingCart.addToCart(selectedItem - 1, itemCount);
                    shoppingCart.saveTxt(basketFile);
                } catch (NumberFormatException nfe) {
                    System.out.println("\nYou need 2 arguments - 2 integers");
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else if (s.equals("end")) {
                break;
            }
            System.out.println("\nYou need 2 arguments");
        }
        sc.close();
        shoppingCart.printCart();
    }
}


