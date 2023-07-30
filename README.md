# Distributed_Systems
 Logical clocks and Message ordering
1. Ordering Techniques Implemented

  ● FIFO :- A FIFO ordered protocol guarantees that messages by the
    same sender are delivered in the order that they were sent. That is, if
    a process multicasts a message m before it multicasts a message m',
    then no correct process receives m' unless it has previously received m.
    
  ● Casual :- If message m0 is causally dependent on message m, any
    correct process must deliver m before m0 Implemented heavily in
    social networks, new groups, comments on web etc., Causal ordering
    automatically implies FIFO ordering, but reverse is not true.
    
  ● Total :- If a correct process p delivers message m before m0
    (independent of the senders), then any other correct process that
    delivers m0 would already have delivered m. Ensures alln receivers
    receive all messages to the group in the same order. Doesn’t care
    about the order in which they were sent by different senders. Used
    for consistent updates on replicated servers.
