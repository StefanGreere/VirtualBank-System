# VirtualBank System  
### Full Banking Simulation Platform in Java  
**Author:** Greere Ioan Ștefan  

---

## Overview  

**VirtualBank System** is a modular, object-oriented banking simulator designed to replicate the core structure and operations of a modern digital bank.  
This project is the **first stage** of a larger system and demonstrates a **complete software architecture**, integrating concepts such as users, accounts, cards, transactions, commands, and exchange rates.  

The system simulates realistic banking operations — from account and card creation to processing transactions and managing exchange rates — all coordinated through a command-driven architecture.  
It reads structured data from JSON files, instantiates the corresponding objects, and executes commands step-by-step, producing consistent and traceable outputs.  

---

## Key Features  

- Full simulation of banking entities: users, accounts, cards, and transactions  
- Modular, extensible object-oriented architecture  
- Command-driven execution pipeline  
- Dynamic exchange rate management  
- JSON-based input/output for easy configuration and testing  
- Use of **design patterns** for scalability and maintainability  

---

## Object-Oriented Design & Architecture  

VirtualBank System is built around the **SOLID principles** of object-oriented design and leverages several **design patterns** to ensure modularity, scalability, and clarity.

### Implemented Design Patterns  

| Pattern | Purpose |
|----------|----------|
| **Singleton** | Ensures a single instance for global classes such as `BankSingleton` and `ExchangeRateManager`, providing centralized control of shared data. |
| **Factory Method** | Simplifies the creation of new objects (e.g., cards, commands) based on runtime input, improving extensibility without altering existing code. |
| **Command Pattern** | Encapsulates actions (commands) as objects, enabling flexible command execution and easy integration of new operations. |
| **Abstract Factory (partial)** | Facilitates the creation of families of related objects (accounts, cards) without specifying concrete classes. |
| **Template Method** | Provides a structure for complex operations that vary slightly between subclasses, promoting code reuse and consistency. |

These design patterns make the system easy to extend: adding new account types, commands, or transaction modes requires minimal changes, respecting **open/closed principles**.

---

## Package and File Structure  

### **Package `fileio`**
Handles reading and parsing data from input JSON files, which describe users, commands, and exchange rates.  
It also manages output generation in JSON format, ensuring full data traceability.

---

### **Package `users`**
Contains two core classes:  

- **`BankSingleton`** — Represents the bank itself, implemented as a Singleton.  
  - Stores all users in a `HashMap` keyed by email.  
  - Provides methods to retrieve users by IBAN, card number, or other attributes.  
  - Central access point for all user-related operations.  

- **`User`** — Represents an individual customer.  
  - Stores all user-related data.  
  - Includes methods for creating and deleting accounts.  
  - Provides encapsulated access to personal and financial information.  

---

### **Package `accounts`**
Defines the **abstract base class** `Account` and its concrete subclasses for different account types.  
Each subclass implements behavior specific to its type, allowing the system to handle multiple financial products seamlessly.  

---

### **Package `cards`**
Implements the card hierarchy.  
An abstract `Card` class defines shared attributes and behavior, while concrete subclasses (e.g., debit, credit) extend it.  
A **Factory Pattern** dynamically creates specific card types based on the command input, supporting extensibility and abstraction.

---

### **Package `commands`**
Contains the full command processing architecture.  

- Commands are modeled using the **Command Pattern**, each implementing a common interface with an `execute()` method.  
- A **Command Factory** instantiates the appropriate command class based on the operation name.  
- This makes it easy to add new commands — simply create a new class and register it in the factory.  
- An abstract base command class handles shared logic, such as linking to output results.  

This approach decouples the command definitions from the main application logic, enhancing flexibility and readability.  

---

### **Package `transaction`**
Contains the parent `Transaction` class and derived types representing different financial operations.  
Transactions are recorded for each user or account, ensuring detailed tracking of all actions performed.  

---

### **Package `rates`**
Implements `ExchangeRateManager`, another **Singleton** responsible for managing currency exchange rates.  
It maintains a nested `HashMap` structure representing direct and indirect conversions between currencies.  
If a direct rate is missing, the system can infer it through transitive connections (currency traversal).  

---

### **Package `main`**
Contains the entry point of the application — the `Main` class.  
Responsibilities include:  
- Initializing core components (users, accounts, rates).  
- Loading input data from JSON files.  
- Generating and executing all commands in sequence.  
- Managing final output generation.  

The main function essentially orchestrates the entire lifecycle of the simulation.

---

## Example Execution Flow  

1. Input data (users, accounts, commands) is read from JSON files.  
2. The bank and exchange rate managers are initialized (Singletons).  
3. Commands are instantiated via the Factory Pattern.  
4. Each command executes its operation, modifying the bank’s internal state.  
5. Output results are written to JSON files for evaluation.  

---

## Scalability and Extensibility  

The VirtualBank System architecture supports easy expansion:  
- Adding a new **account** or **card** type → Create a subclass and register it in the factory.  
- Adding a new **command** → Implement a new command class and integrate it into the Command Factory.  
- Adding new **transaction types** → Derive a new subclass from `Transaction`.  

Because of this modular design, future stages can easily integrate:  
- Interest calculation, reporting, or loan systems.  
- RESTful or GUI interfaces.  
- Database persistence instead of JSON I/O.  

---

## Technologies and Tools  

- **Language:** Java  
- **Paradigm:** Object-Oriented Programming (OOP)  
- **Data Format:** JSON (input/output)  
- **Architecture:** Modular, extensible design using multiple OOP design patterns  
- **Tools:** IntelliJ IDEA, JDK 11+, GitHub  

---

## Conclusion  

**VirtualBank System** is a full-featured object-oriented banking simulation that emphasizes software design, scalability, and extensibility.  
Through the use of key design patterns and a layered architecture, it successfully models a realistic financial environment while maintaining clean code and modular expansion potential.  

This project serves as both a demonstration of advanced OOP concepts and a solid foundation for further development into a fully functional banking platform.
