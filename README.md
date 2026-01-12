# In-Memory File System (LLD)

A robust, in-memory file system implementation in Java, demonstrating Low-Level Design (LLD) principles, design patterns, and clean architecture.

## Overview

This project implements a hierarchical file system where users can create directories, create files, write content to files, read from files, and manage permissions.

## Key Features

- **Hierarchical Structure**: Supports nested directories and files.
- **File Operations**:
  - `mkdir`: Create new directories.
  - `touch`: Create new empty files.
  - `ls`: List contents of a directory.
  - `write`: Append content to files.
  - `read`: Read content from files.
  - `rm`: Remove files or directories.
- **Permissions**: Each node (file or directory) has a set of permissions (`READ`, `WRITE`, `DELETE`, `EXECUTE`).
- **Metadata**: Automatically tracks `createdAt` and `updatedAt` timestamps for all nodes.

## Design Patterns Used

1.  **Composite Design Pattern**:
    - `Node` (Component): Abstract base class for both files and directories.
    - `FileNode` (Leaf): Represents a file.
    - `Directory` (Composite): Represents a directory that can contain other nodes.
2.  **Service Layer Pattern**:
    - `FileSystem` service encapsulates all logic for path traversal and file operations, keeping the models clean.

## Detailed Implementation

### 1. Models (`src/models/`)

- **`Node.java`**: The absolute parent of any entity in our file system. It holds common attributes like `name`, `parent`, `permissions`, and timestamps (`createdAt`, `updatedAt`). It defines the `getPath()` logic which recursively builds the full path from the root.
- **`FileNode.java`**: Extends `Node`. It represents a leaf node in the hierarchy. It includes a `StringBuilder` to store file content and manages `read()` and `write()` operations.
- **`Directory.java`**: Extends `Node`. It represents a composite node. It contains a `Map<String, Node>` to store its child nodes (which can be either files or other directories).

### 2. Service Layer (`src/service/`)

- **`FileSystem.java`**: The orchestrator. It manages the root directory and provides a high-level API for the user.
  - **Path Traversal**: It includes private helper methods like `traverseToParent(path)` which resolves a string path (e.g., `/home/user/docs`) into the corresponding `Directory` object.
  - **Operations**: It wraps the model logic into user-friendly commands like `mkdir`, `touch`, `write`, `read`, `ls`, and `rm`.

### 3. Enums (`src/enums/`)

- **`NodePermission.java`**: Defines the access control levels: `READ`, `WRITE`, `DELETE`, and `EXECUTE`.

## Project Structure

```text
src/
├── enums/
│   └── NodePermission.java    # Enum for READ, WRITE, DELETE, EXECUTE
├── models/
│   ├── Node.java              # Abstract base class (Composite Component)
│   ├── FileNode.java          # File implementation (Leaf)
│   └── Directory.java         # Directory implementation (Composite)
├── service/
│   └── FileSystem.java        # Core logic for FS operations
└── App.java                   # Entry point and Test Suite
```

## Test Suite (`App.java`)

The `App.java` file is configured as a standalone test runner. It initializes a new `FileSystem` and executes a series of operations to validate:

- Deep path creation.
- File integrity (write then read).
- Directory listing accuracy.
- Exception handling for invalid paths or unauthorized operations.

## How to Run

1.  **Compile**:
    ```bash
    javac -d bin -sourcepath src src/App.java
    ```
2.  **Run**:
    ```bash
    java -cp bin App
    ```
