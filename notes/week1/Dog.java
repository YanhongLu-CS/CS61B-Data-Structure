public class Dog {
    // public static void makeNoise() {
    //     // static means this function belong to the class, instead of a specific object.
    //     System.out.println("Bark!");
    // }
    // static methods cannot access "my" instance variables, as there is no me. 
    public int weight;
    public Dog(int startingWeight) {
        weight = startingWeight;
    }

    public static Dog maxDog(Dog d1, Dog d2) {
        if (d1.weight > d2.weight) {
            return d1;
        }
        return d2;
    }

    public void makeNoise() {
        if (weight < 10) {
            System.out.println("abc");
        } else if (weight < 30) {
            System.out.println("efg");
        } else {
            System.out.println("hij");
        }
    }
}
