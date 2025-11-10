import java.util.Scanner;

public class EventProcessingSystem
{
    String eventQueue[];
    int maxSize;
    int front;
    int rear;
    int count;
    Scanner scanner;

    public EventProcessingSystem()
    {
        maxSize = 100; // Initial capacity
        eventQueue = new String[maxSize];
        front = 0;
        rear = -1;
        count = 0;
        scanner = new Scanner(System.in);
    }

    public void addEvent(String event)
    {
        if (count == maxSize)
        {
            System.out.println("Event queue is full.");
        }
        else
        {
            rear = (rear + 1) % maxSize;
            eventQueue[rear] = event;
            count++;
            System.out.println("Event " + event + " added to the queue.");
        }
    }

    public void processNextEvent()
    {
        if (count == 0)
        {
            System.out.println("No events to process.");
        }
        else
        {
            String event = eventQueue[front];
            System.out.println("Processed event: " + event + " ");
            front = (front + 1) % maxSize;
            count--;
        }
    }

    public void displayPendingEvents() {
        if (count == 0)
        {
            System.out.println("No pending events.");
        }
        else
        {
            System.out.println("Pending Events:");
            int current = front;
            for (int i = 0; i < count; i++)
            {
                System.out.println((i + 1) + ". " + eventQueue[current]);
                current = (current + 1) % maxSize;
            }
        }
    }

    public void cancelEvent(String eventName)
    {
        if (count == 0)
        {
            System.out.println("Event " + eventName + " not found or already processed.");
            return;
        }

        boolean found = false;
        int current = front;

        // Find the event to cancel
        for (int i = 0; i < count; i++)
        {
            if (eventQueue[current] != null && eventQueue[current].equals(eventName))
            {
                found = true;
                // Shift elements to remove the found event
                int indexToRemove = current;
                for (int j = 0; j < count - i - 1; j++)
                {
                    int nextIndex = (indexToRemove + 1) % maxSize;
                    eventQueue[indexToRemove] = eventQueue[nextIndex];
                    indexToRemove = nextIndex;
                }
                rear = (rear - 1 + maxSize) % maxSize;
                count--;
                System.out.println("Event " + eventName + " has been canceled.");
                break;
            }
            current = (current + 1) % maxSize;
        }

        if (!found)
        {
            System.out.println("Event " + eventName + " not found or already processed.");
        }
    }

    public void menu()
    {
        while (true)
        {
            System.out.println("\n--- EVENT MENU ---");
            System.out.println("1. Add Event");
            System.out.println("2. Process Next Event");
            System.out.println("3. Display Pending Events");
            System.out.println("4. Cancel an Event");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice)
            {
                case "1":
                    System.out.print("Enter event name: ");
                    String event = scanner.nextLine();
                    addEvent(event);
                    break;
                case "2":
                    processNextEvent();
                    break;
                case "3":
                    displayPendingEvents();
                    break;
                case "4":
                    System.out.print("Enter event name to cancel: ");
                    String eventName = scanner.nextLine();
                    cancelEvent(eventName);
                    break;
                case "5":
                    System.out.println("Exiting Event Processing System.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }
        }
    }

    public static void main(String s[])
    {
        EventProcessingSystem system = new EventProcessingSystem();
        system.menu();
    }
}