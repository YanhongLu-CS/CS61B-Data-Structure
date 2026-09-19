import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/** Tests ArrayDeque using the same deque contract as LinkedListDequeTest. */
public class AListDequetest {
    public static boolean checkEmpty(boolean expected, boolean actual) {
        return checkEquals("isEmpty()", expected, actual);
    }

    public static boolean checkSize(int expected, int actual) {
        return checkEquals("size()", expected, actual);
    }

    private static boolean checkEquals(String operation, Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) {
            throw new AssertionError(operation + " returned " + actual
                    + ", but expected: " + expected);
        }
        return true;
    }

    public static void printTestStatus(boolean passed) {
        if (!passed) {
            throw new AssertionError("Test failed!");
        }
        System.out.println("Test passed!\n");
    }

    /** Checks logical size, emptiness, and order without inspecting the backing array. */
    private static <T> void checkContents(ArrayDeque<T> deque, List<T> expected) {
        checkSize(expected.size(), deque.size());
        checkEmpty(expected.isEmpty(), deque.isEmpty());
        for (int i = 0; i < expected.size(); i += 1) {
            checkEquals("get(" + i + ")", expected.get(i), deque.get(i));
        }
        checkSize(expected.size(), deque.size());
        checkEmpty(expected.isEmpty(), deque.isEmpty());
    }

    @SafeVarargs
    private static <T> void checkContents(ArrayDeque<T> deque, T... expected) {
        checkContents(deque, java.util.Arrays.asList(expected));
    }

    public static void constructorTest() {
        System.out.println("Running constructor test.");
        checkContents(new ArrayDeque<Integer>());
        ArrayDeque<String> single = new ArrayDeque<>("only");
        checkContents(single, "only");
        checkEquals("removeLast()", "only", single.removeLast());
        checkContents(single);
        single.addFirst("reused");
        checkContents(single, "reused");
        printTestStatus(true);
    }

    public static void addIsEmptySizeTest() {
        System.out.println("Running add/isEmpty/size test.");
        ArrayDeque<String> deque = new ArrayDeque<>();
        checkContents(deque);
        deque.addFirst("middle");
        checkContents(deque, "middle");
        deque.addFirst("front");
        checkContents(deque, "front", "middle");
        deque.addLast("back");
        checkContents(deque, "front", "middle", "back");
        deque.addLast("last");
        checkContents(deque, "front", "middle", "back", "last");
        printTestStatus(true);
    }

    public static void addRemoveTest() {
        System.out.println("Running add/remove and empty reuse test.");
        // Exercise all four ways to add and remove the only element.
        for (int addEnd = 0; addEnd < 2; addEnd += 1) {
            for (int removeEnd = 0; removeEnd < 2; removeEnd += 1) {
                ArrayDeque<Integer> deque = new ArrayDeque<>();
                for (int round = 0; round < 3; round += 1) {
                    checkEquals("empty removeFirst()", null, deque.removeFirst());
                    checkEquals("empty removeLast()", null, deque.removeLast());
                    checkContents(deque);
                    if (addEnd == 0) {
                        deque.addFirst(round);
                    } else {
                        deque.addLast(round);
                    }
                    checkContents(deque, round);
                    Integer removed = removeEnd == 0 ? deque.removeFirst() : deque.removeLast();
                    checkEquals("singleton removal", round, removed);
                    checkContents(deque);
                }
            }
        }
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        for (int i = 0; i < 5; i += 1) {
            deque.addLast(i);
        }
        checkEquals("removeFirst()", 0, deque.removeFirst());
        checkContents(deque, 1, 2, 3, 4);
        checkEquals("removeLast()", 4, deque.removeLast());
        checkContents(deque, 1, 2, 3);
        checkEquals("removeLast()", 3, deque.removeLast());
        checkContents(deque, 1, 2);
        checkEquals("removeFirst()", 1, deque.removeFirst());
        checkContents(deque, 2);
        checkEquals("removeLast()", 2, deque.removeLast());
        checkContents(deque);
        printTestStatus(true);
    }

    public static void copyConstructorTest() {
        System.out.println("Running copy constructor test.");
        ArrayDeque<String> empty = new ArrayDeque<>();
        ArrayDeque<String> emptyCopy = new ArrayDeque<>(empty);
        checkContents(emptyCopy);
        emptyCopy.addLast("copy");
        checkContents(empty);
        empty.addFirst("original");
        checkContents(emptyCopy, "copy");

        ArrayDeque<Integer> original = new ArrayDeque<>(1);
        ArrayDeque<Integer> singleCopy = new ArrayDeque<>(original);
        checkContents(singleCopy, 1);
        original.addLast(2);
        original.addLast(3);
        ArrayDeque<Integer> copy = new ArrayDeque<>(original);
        checkContents(copy, 1, 2, 3);
        original.removeFirst();
        original.addLast(4);
        checkContents(copy, 1, 2, 3);
        copy.removeLast();
        copy.addFirst(0);
        checkContents(copy, 0, 1, 2);
        checkContents(original, 2, 3, 4);
        checkContents(singleCopy, 1);
        printTestStatus(true);
    }

    public static void nullAndDuplicateTest() {
        System.out.println("Running null and duplicate item test.");
        ArrayDeque<String> deque = new ArrayDeque<>((String) null);
        checkContents(deque, (String) null);
        deque.addFirst("same");
        deque.addLast("same");
        deque.addLast(null);
        checkContents(deque, "same", null, "same", null);
        checkEquals("removeLast()", null, deque.removeLast());
        checkContents(deque, "same", null, "same");
        checkEquals("removeFirst()", "same", deque.removeFirst());
        checkEquals("removeFirst()", null, deque.removeFirst());
        checkContents(deque, "same");
        checkEquals("removeLast()", "same", deque.removeLast());
        checkContents(deque);
        printTestStatus(true);
    }

    private static void checkPrinted(String expected, ArrayDeque<?> deque) {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream capturedOut = new PrintStream(output);
        try {
            System.setOut(capturedOut);
            deque.printDeque();
            capturedOut.flush();
        } finally {
            System.setOut(originalOut);
            capturedOut.close();
        }
        checkEquals("printDeque()", expected, output.toString());
    }

    public static void printDequeTest() {
        System.out.println("Running printDeque test.");
        ArrayDeque<String> deque = new ArrayDeque<>();
        checkPrinted("", deque);
        checkContents(deque);
        deque.addLast("front");
        checkPrinted("front ", deque);
        deque.addLast("middle");
        deque.addLast("back");
        // Use the same space-separated output contract as LinkedListDeque.
        checkPrinted("front middle back ", deque);
        checkContents(deque, "front", "middle", "back");
        printTestStatus(true);
    }

    public static void mixedOperationsTest() {
        System.out.println("Running deterministic mixed operations test.");
        Random random = new Random(61);
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        List<Integer> expected = new ArrayList<>();
        for (int step = 0; step < 2000; step += 1) {
            int operation = random.nextInt(4);
            int value = random.nextInt(20);
            if (operation == 0) {
                deque.addFirst(value);
                expected.add(0, value);
            } else if (operation == 1) {
                deque.addLast(value);
                expected.add(value);
            } else if (operation == 2) {
                Integer item = expected.isEmpty() ? null : expected.remove(0);
                checkEquals("removeFirst() at step " + step, item, deque.removeFirst());
            } else {
                Integer item = expected.isEmpty() ? null : expected.remove(expected.size() - 1);
                checkEquals("removeLast() at step " + step, item, deque.removeLast());
            }
            checkContents(deque, expected);
        }
        while (!expected.isEmpty()) {
            checkEquals("drain removeLast()", expected.remove(expected.size() - 1),
                    deque.removeLast());
            checkContents(deque, expected);
        }
        deque.addLast(42);
        checkContents(deque, 42);
        printTestStatus(true);
    }

    public static void invalidIndexTest() {
        System.out.println("Running invalid index test.");
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        checkInvalidIndices(deque);
        deque.addLast(10);
        deque.addLast(20);
        checkInvalidIndices(deque);
        checkContents(deque, 10, 20);
        deque.removeLast();
        checkInvalidIndices(deque);
        checkContents(deque, 10);
        deque.removeLast();
        checkInvalidIndices(deque);
        checkContents(deque);
        printTestStatus(true);
    }

    private static void checkInvalidIndices(ArrayDeque<?> deque) {
        int[] indices = {-1, Integer.MIN_VALUE, deque.size(),
            deque.size() + 1, Integer.MAX_VALUE};
        for (int index : indices) {
            checkEquals("get(" + index + ")", null, deque.get(index));
        }
    }

    /** Cross several capacity boundaries, then drain and reuse the deque. */
    public static void resizeTest(boolean atFront) {
        System.out.println("Running resize test, add at front: " + atFront);
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        List<Integer> expected = new ArrayList<>();
        for (int round = 0; round < 2; round += 1) {
            for (int i = 0; i < 1024; i += 1) {
                if (atFront) {
                    deque.addFirst(i);
                    expected.add(0, i);
                } else {
                    deque.addLast(i);
                    expected.add(i);
                }
                checkContents(deque, expected);
            }
            ArrayDeque<Integer> copy = new ArrayDeque<>(deque);
            checkContents(copy, expected);
            while (!expected.isEmpty()) {
                if (expected.size() % 2 == 0) {
                    checkEquals("drain removeFirst()", expected.remove(0), deque.removeFirst());
                } else {
                    checkEquals("drain removeLast()", expected.remove(expected.size() - 1),
                            deque.removeLast());
                }
                checkContents(deque, expected);
            }
            checkSize(1024, copy.size());
            checkEquals("empty removeFirst()", null, deque.removeFirst());
            checkEquals("empty removeLast()", null, deque.removeLast());
            checkContents(deque);
        }
        printTestStatus(true);
    }

    /** Repeatedly move both ends, then grow while the logical start is displaced. */
    public static void movingEndsTest() {
        System.out.println("Running moving ends test.");
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        List<Integer> expected = new ArrayList<>();
        for (int i = 0; i < 64; i += 1) {
            deque.addLast(i);
            expected.add(i);
        }
        for (int i = 0; i < 300; i += 1) {
            checkEquals("moving removeFirst()", expected.remove(0), deque.removeFirst());
            deque.addLast(1000 + i);
            expected.add(1000 + i);
            checkContents(deque, expected);
        }
        for (int i = 0; i < 300; i += 1) {
            checkEquals("moving removeLast()", expected.remove(expected.size() - 1),
                    deque.removeLast());
            deque.addFirst(-i);
            expected.add(0, -i);
            checkContents(deque, expected);
        }
        for (int i = 0; i < 300; i += 1) {
            deque.addLast(i);
            expected.add(i);
            checkContents(deque, expected);
        }
        checkContents(new ArrayDeque<>(deque), expected);
        printTestStatus(true);
    }

    private static int runTest(String name, Runnable test) {
        try {
            test.run();
            return 0;
        } catch (AssertionError | RuntimeException failure) {
            System.out.println("FAILED: " + name + " - " + failure);
            failure.printStackTrace(System.out);
            return 1;
        }
    }

    public static void main(String[] args) {
        int failures = 0;
        failures += runTest("constructors", AListDequetest::constructorTest);
        failures += runTest("add/isEmpty/size", AListDequetest::addIsEmptySizeTest);
        failures += runTest("add/remove", AListDequetest::addRemoveTest);
        failures += runTest("copy constructor", AListDequetest::copyConstructorTest);
        failures += runTest("null/duplicates", AListDequetest::nullAndDuplicateTest);
        failures += runTest("printDeque", AListDequetest::printDequeTest);
        failures += runTest("invalid indices", AListDequetest::invalidIndexTest);
        failures += runTest("resize via addLast", () -> resizeTest(false));
        failures += runTest("resize via addFirst", () -> resizeTest(true));
        failures += runTest("moving ends", AListDequetest::movingEndsTest);
        failures += runTest("mixed operations", AListDequetest::mixedOperationsTest);
        if (failures > 0) {
            throw new AssertionError(failures + " of 11 test groups failed.");
        }
        System.out.println("All 11 test groups passed.");
    }
}
