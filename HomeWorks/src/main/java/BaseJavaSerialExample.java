public class BaseJavaSerialExample {

    RecipesSearchResponseItem item;

//        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("serial_file.txt"));
//        oos.writeObject(item);
//        oos.close();

    ObjectInputStream ois = new ObjectInputStream(new FileInputStream("serial_file.txt"));
    item = (RecipesSearchResponseItem) ois.readObject();
        System.out.println(item);
      }
}
