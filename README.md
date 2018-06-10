# WotMatchmakerSimulator
A World of Tanks matchmaker simulator framework

This is a little framework written in Java which shall enable people to write and to test their own matchmaker algorithms for World of Tanks.
The goal is either to figure out that there is a great matchmaker algorithm that WG should adapt - or to find out that writing a great matchmaking algorithm is fudging hard.

Please excuse my Java. I'm a C++ guy and this is my first real Java project... :P

## What exists/works so far
- Database table with information about existing tanks
- Database table with information about tank usage
- Queue that is filled automatically. The ratio of the tanks in the queue reflects the percentage of tanks used by players in the game.
- API to 
  - retrieve tanks from the queue
  - form and store matches (in the database)
  - store unmatched tanks (in the database)
- (Bad) example matchmaker
- Analysis of matchmaking result via SQL (via DB Browser for SQLite)
- Loading (injection), configuration and running of a self-written matchmaker into the simulator (from jar)
  The self written matchmaker has to inherit IMatchmaker or ThreadedMatchmaker

## What is still missing (most important stuff)
- Platoons
  - adding to the queue
  - retrieving form the queue
- A non-stupid matchmaker example
- Better analysis facilities
- Documentation
More details about what needs to be done can be found in the todo.txt and as TODO comments within the code.

World of Tanks is a trademark of Wargaming.net 
