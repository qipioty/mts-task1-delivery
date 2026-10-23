# Delivery Service Console Simulator

Console application that simulates a small delivery service. Everything works in memory: no database, files or network.

## Features

- Create orders with one or more items
- Choose a delivery method: standard (courier), express (courier), pickup
- See delivery cost and estimated time
- Assign a courier to an order (only if the delivery method requires one)
- Change order status following allowed transitions
- Input validation with clear error messages

## Order statuses

`CREATED` → `COURIER_ASSIGNED` → `IN_DELIVERY` → `DELIVERED`

An order can be `CANCELLED` from `CREATED` or `COURIER_ASSIGNED`.
`COURIER_ASSIGNED` is set only by assigning a courier.
Pickup orders have no courier and go from `CREATED` straight to `IN_DELIVERY`.

## Delivery methods

| Method   | Cost   | Time   | Courier |
|----------|--------|--------|---------|
| Standard | 300    | 3 days | yes     |
| Express  | 600    | 1 day  | yes     |
| Pickup   | 0      | 0 days | no      |

## Project structure

- `delivery` — `DeliveryMethod` interface and its implementations
- `model` — `Order`, `OrderItem`, `Client`, `Courier`, `Address`, `OrderStatus`
- `service` — `OrderService` (orders and couriers management)
- `ui` — `ConsoleMenu` (console input and output)

## Run

Requires Java 17+ and Maven.

    mvn compile exec:java -Dexec.mainClass=com.example.delivery.Main

Or run `Main` from IntelliJ IDEA.

## Menu

1. Create order
2. Show orders
3. Choose delivery method
4. Assign courier
5. Change order status
6. Add courier
0. Exit