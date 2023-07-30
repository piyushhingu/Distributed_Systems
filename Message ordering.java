import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Message {
    private int senderId;
    private int receiverId;
    private String content;
    private int[] vectorClock;
    private int delay;
    private int NumProcesses;
    private int[] timestamp;
    private long deliveryTime;
    private OrderingSystemTotal sender;
    private Message message;
    private int globalSequenceNumber;

    public Message(int senderId, int receiverId, String content, int[] vectorClock, int delay, int NumProcesses) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.vectorClock = vectorClock != null ? vectorClock.clone() : new int[3];
        this.delay = delay;
        this.NumProcesses = NumProcesses;
        this.timestamp = new int[3];
    }

    public OrderingSystemTotal getSender() {
        return sender;
    }

    public Message getMessage() {
        return message;
    }

    public int getGlobalSequenceNumber() {
        return globalSequenceNumber;
    }

    public int getSenderId() {
        return senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public String getContent() {
        return content;
    }

    public int[] getVectorClock() {
        return vectorClock.clone();
    }

    public int[] setVectorClock(int[] vc) {
        for (int i = 0; i < NumProcesses; i++) {
            vectorClock[i] = vc[i];
        }
        return vectorClock;
    }

    public int getDelay() {
        return delay;
    }

    public int getnumProcesses() {
        return NumProcesses;
    }

    public int[] getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int[] timestamp) {
        this.timestamp = timestamp;
    }

    public long getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(long deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Message orElse(Object object) {
        return null;
    }
}

class Message1 {
    private int senderId;
    private int receiverId;
    private String content;
    private int[] vectorClock;
    private int delay;
    private int NumProcesses;
    private int[] timestamp;
    private long deliveryTime;
    private String message;
    private int globalSequenceNumber;

    public Message1(int senderId, int receiverId, String content, int numProcesses, int delay,
            int globalSequenceNumber) {
        this.message = content;
        this.globalSequenceNumber = globalSequenceNumber;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.vectorClock = vectorClock != null ? vectorClock.clone() : new int[3];
        this.delay = delay;
        this.NumProcesses = numProcesses;
        this.timestamp = new int[3];
    }

    public String getMessage() {
        return message;
    }

    public int getGlobalSequenceNumber() {
        return globalSequenceNumber;
    }

    public int getSenderId() {
        return senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public String getContent() {
        return content;
    }

    public int[] getVectorClock() {
        return vectorClock.clone();
    }

    public int[] setVectorClock(int[] vc) {
        for (int i = 0; i < NumProcesses; i++) {
            vectorClock[i] = vc[i];
        }
        return vectorClock;
    }

    public int getDelay() {
        return delay;
    }

    public int getnumProcesses() {
        return NumProcesses;
    }

    public int[] getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int[] timestamp) {
        this.timestamp = timestamp;
    }

    public long getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(long deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Message orElse(Object object) {
        return null;
    }
}

class OrderingSystemFifo {
    private int numProcesses;
    private List<Message> buffer;
    private int[] localClocks;

    public OrderingSystemFifo(int numProcesses) {
        this.numProcesses = numProcesses;
        this.buffer = new ArrayList<>();
        this.localClocks = new int[numProcesses];
    }

    public void sendMessage(Message message) {
        // update local clock
        localClocks[message.getSenderId()]++;

        // set vector clock for message
        int[] vectorClock = localClocks.clone();

        // add delay to message
        int delay = message.getDelay();
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // create new message object and add to buffer
        Message newMessage = new Message(message.getSenderId(), message.getReceiverId(),
                message.getContent(), vectorClock, delay, numProcesses);
        buffer.add(newMessage);

        System.out.println("Message sent from process " + message.getSenderId() + " to process " +
                message.getReceiverId() + " with vector clock : " + Arrays.toString(vectorClock) + ": "
                + message.getContent());
    }

    public boolean isBufferEmpty() {
        return buffer.isEmpty();
    }

    public void receiveMessage() {

        // get message with lowest timestamp (FIFO ordering)
        Message message = Collections.min(buffer, Comparator.comparingInt(m -> m.getVectorClock()[m.getSenderId()]));

        try {
            Thread.sleep(message.getDelay());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // update local clock
        localClocks[message.getReceiverId()]++;

        // remove message from buffer
        buffer.remove(message);

        System.out.println("Message received by process " + message.getReceiverId()
                + " with vector clock : " + Arrays.toString(message.getVectorClock()) +
                ": " + message.getContent());

    }
}

class OrderingSystemCausal {
    private int numProcesses;
    private List<Message> buffer;
    private int[] localClocks;

    public OrderingSystemCausal(int numProcesses) {
        this.numProcesses = numProcesses;
        this.buffer = new ArrayList<>();
        this.localClocks = new int[numProcesses];
    }

    public void sendMessage(Message message) {
        // update local clock
        localClocks[message.getSenderId()]++;

        // set vector clock for message
        int[] vectorClock = localClocks.clone();

        // add delay to message
        int delay = message.getDelay();
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // create new message object and add to buffer
        Message newMessage = new Message(message.getSenderId(), message.getReceiverId(),
                message.getContent(), vectorClock, delay, numProcesses);
        buffer.add(newMessage);

        System.out.println("Message sent from process " + message.getSenderId() + " to process " +
                message.getReceiverId() + " with vector clock : " + Arrays.toString(vectorClock) + ": "
                + message.getContent());
    }

    public boolean isBufferEmpty() {
        return buffer.isEmpty();
    }

    public void receiveMessage() {

        // get message with lowest timestamp (FIFO ordering)
        Message message = Collections.min(buffer, Comparator.comparingInt(m -> m.getVectorClock()[m.getSenderId()]));

        try {
            Thread.sleep(message.getDelay());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // check if message can be delivered (causal ordering)
        boolean canDeliver = true;
        for (int i = 0; i < numProcesses; i++) {
            if (message.getVectorClock()[i] > localClocks[i]) {
                canDeliver = false;
                break;
            }
        }

        if (canDeliver) {
            // update local clock
            localClocks[message.getReceiverId()]++;

            // remove message from buffer
            buffer.remove(message);

            System.out.println("Message received by process " + message.getReceiverId()
                    + " with vector clock : " + Arrays.toString(message.getVectorClock()) +
                    ": " + message.getContent());
        } else {
            // message cannot be delivered yet, wait for next receive call
            System.out.println("Message cannot be delivered yet");
        }
    }
}

class OrderingSystemTotal {
    private int numProcesses;
    private List<Message1> buffer;
    private int[] localClocks;
    private int globalSequenceNumber;

    public OrderingSystemTotal(int numProcesses) {
        this.numProcesses = numProcesses;
        this.buffer = new ArrayList<Message1>();
        this.localClocks = new int[numProcesses];
        this.globalSequenceNumber = globalSequenceNumber;
    }

    public synchronized void sendMessage(Message1 message) {
        // update local clock
        localClocks[message.getSenderId()]++;

        // add delay to message
        int delay = message.getDelay();

        Message1 message1 = new Message1(message.getSenderId(), message.getReceiverId(),
                message.getContent(), numProcesses, delay,
                message.getGlobalSequenceNumber());
        buffer.add(message1);

        System.out.println("Message sent from process " + message.getSenderId() + " to process " +
                message.getReceiverId() + " with SequenceNumber : " + message.getGlobalSequenceNumber() + ": "
                + message.getContent());

        // increment global sequence number
        globalSequenceNumber++;
    }

    public synchronized boolean isBufferEmpty() {
        return buffer.isEmpty();
    }

    public synchronized void receiveMessage() {

        // get message with lowest global sequence number
        Message1 message = buffer.stream().min(Comparator.comparingInt(Message1::getGlobalSequenceNumber))
                .orElse(null);

        if (message == null) {
            return;
        }

        // update local clock
        int[] vectorClock = message.getVectorClock();
        for (int i = 0; i < numProcesses; i++) {
            localClocks[i] = Math.max(localClocks[i], vectorClock[i]);
        }
        localClocks[message.getReceiverId()]++;

        // remove message from buffer
        buffer.remove(message);

        System.out.println("Message received by process " + message.getReceiverId()
                + " with global_SequenceNumber : " + message.getGlobalSequenceNumber() +
                ": " + message.getContent());
    }
}

public class main1 {
    public static void main(String[] args) throws InterruptedException {
        int numProcesses = 3;

        // for (int i = 0; i < numProcesses; i++) {
        // final int processId = i;
        // generate a random delay for the message
        int delay1 = (int) (Math.random() * 500);
        int delay2 = (int) (Math.random() * 500);
        int delay3 = (int) (Math.random() * 500);

        Thread thread0 = new Thread(() -> {
            while (true) {

                OrderingSystemFifo orderingSystemFifo = new OrderingSystemFifo(numProcesses);

                System.out.println("===========FIFO===============");

                // send a message to a random process
                Message m1 = new Message(2, 1, "Hello", null, delay1, numProcesses);
                orderingSystemFifo.sendMessage(m1);

                Message m2 = new Message(1, 2, "Hi", null, delay2, numProcesses);
                orderingSystemFifo.sendMessage(m2);

                Message m3 = new Message(0, 2, "Hey", null, delay3, numProcesses);
                orderingSystemFifo.sendMessage(m3);

                // receive a message
                while (!orderingSystemFifo.isBufferEmpty()) {
                    orderingSystemFifo.receiveMessage();
                }
                if (orderingSystemFifo.isBufferEmpty() == true)
                    break;
            }
        });
        thread0.start();

        thread0.join();

        Thread thread1 = new Thread(() -> {
            while (true) {

                OrderingSystemCausal orderingSystemCausal = new OrderingSystemCausal(numProcesses);

                System.out.println("===========CAUSAL===============");

                // send messages
                Message m4 = new Message(1, 2, "Hello", null, 8, numProcesses);
                orderingSystemCausal.sendMessage(m4);

                Message m5 = new Message(2, 1, "Hi", null, 4, numProcesses);
                orderingSystemCausal.sendMessage(m5);

                Message m6 = new Message(0, 2, "Hey", null, 2, numProcesses);
                orderingSystemCausal.sendMessage(m6);

                // receive messages using causal ordering
                while (!orderingSystemCausal.isBufferEmpty()) {
                    orderingSystemCausal.receiveMessage();
                }
                if (orderingSystemCausal.isBufferEmpty() == true)
                    break;
            }

        });
        thread1.start();
        thread1.join();

        Thread thread2 = new Thread(() -> {
            while (true) {

                OrderingSystemTotal orderingSystemTotal = new OrderingSystemTotal(numProcesses);

                System.out.println("===========TOTAL===============");

                // public Message1(int senderId, int receiverId, String content, int
                // numProcesses,int delay, int globalSequenceNumber)

                // send messages
                Message1 m7 = new Message1(2, 1, "Hello", numProcesses,
                        delay1, 2);
                orderingSystemTotal.sendMessage(m7);

                Message1 m8 = new Message1(0, 1, "Hi", numProcesses, delay1, 0);
                orderingSystemTotal.sendMessage(m8);

                Message1 m9 = new Message1(1, 2, "Hey", numProcesses, delay1, 1);
                orderingSystemTotal.sendMessage(m9);

                // receive messages using total ordering
                while (!orderingSystemTotal.isBufferEmpty()) {
                    orderingSystemTotal.receiveMessage();
                }
                if (orderingSystemTotal.isBufferEmpty() == true)
                    break;
            }
        });
        thread2.start();
        thread2.join();
    }

    // }
}