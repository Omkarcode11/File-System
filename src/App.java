import service.FileSystem;

public class App {
    public static void main(String[] args) {
        try {
            System.out.println("=== In-Memory File System Test Suite ===\n");

            FileSystem fs = new FileSystem();

            // 1. Test Directory Creation (mkdir)
            System.out.println("--- 1. Testing mkdir ---");
            fs.mkdir("/home");
            fs.mkdir("/home/user");
            fs.mkdir("/home/user/documents");
            fs.ls("/home/user");
            System.out.println();

            // 2. Test File Creation (touch)
            System.out.println("--- 2. Testing touch ---");
            fs.touch("/home/user/readme.txt");
            fs.touch("/home/user/documents/notes.txt");
            fs.ls("/home/user");
            fs.ls("/home/user/documents");
            System.out.println();

            // 3. Test Writing to File
            System.out.println("--- 3. Testing write ---");
            fs.write("/home/user/readme.txt", "Hello World! Welcome to the new File System.");
            fs.write("/home/user/readme.txt", " This is appended content.");
            System.out.println("Successfully wrote to /home/user/readme.txt");
            System.out.println();

            // 4. Test Reading from File
            System.out.println("--- 4. Testing read ---");
            String content = fs.read("/home/user/readme.txt");
            System.out.println("Content of /home/user/readme.txt: " + content);
            System.out.println();

            // 5. Test Path Traversal and Deep LS
            System.out.println("--- 5. Testing Nested LS ---");
            fs.ls("/");
            fs.ls("/home");
            System.out.println();

            // 6. Test Deletion (rm)
            System.out.println("--- 6. Testing rm ---");
            fs.rm("/home/user/documents/notes.txt");
            System.out.println("Removed /home/user/documents/notes.txt");
            fs.ls("/home/user/documents");
            System.out.println();

            // 7. Testing Edge Cases
            System.out.println("--- 7. Testing Edge Cases ---");
            try {
                System.out.println("Testing invalid path:");
                fs.ls("/home/invalid");
            } catch (Exception e) {
                System.out.println("Caught expected exception: " + e.getMessage());
            }

            try {
                System.out.println("\nTesting rm on non-existent file:");
                fs.rm("/home/user/ghost.txt");
            } catch (Exception e) {
                System.out.println("Caught expected exception: " + e.getMessage());
            }

            try {
                System.out.println("\nTesting write to a directory:");
                fs.write("/home/user", "Some data");
            } catch (Exception e) {
                System.out.println("Caught expected exception: " + e.getMessage());
            }

            System.out.println("\n--- All tests completed successfully! ---");

        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
