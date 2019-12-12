# CPU-Scheduling-Simulator
Simulating some algorithms for CPU scheduling.

1-Non-Preemptive Shortest Job First [SJF].
2-Shortest- Remaining Time First [SRTF].
3- Non-preemptive Priority Scheduling [with the solving of starvation problem]
4- AG Scheduling

[AG-Scheduling] -> Suggested algorithm.
* In this algorithm, all processes are provided with a static time to execute called quantum time.
* A new factor -AG factor- is suggested to attach with each submitted process. This factor sums the effects of all three basic factors
  (priority, arrival time and burst time).
  [i.e. AG-Factor = Priority + Arrival Time + Burst Time]
* Once a process is executed for given time period, it’s called Non-preemptive AG till the finishing of (ceil (50%)) of its Quantum time,
  after that it’s converted to preemptive AG
* We have 3 scenarios of the running process
[i]. The running process used all its quantum time and it still have job to do 
        In this case, add this process to the end of the queue, then increases its Quantum time by (ceil(10% of the (mean of Quantum)))).
[ii]. The running process didn’t use all its quantum time based on another process converted from ready to running.
        In this case, add this process to the end of the queue, and then increase its Quantum time by the remaining unused Quantum 
        time of this process.
[iii]. The running process finished its job 
        In this case, set its quantum time to zero and remove it from ready queue and add it to the die list.


[Test Case for AG scheduling] 
process       AT        BT       Priority        QT
  P1          0         17          4             4
  P2          3         6           9             4
  P3          4         10          3             4
  P4          29        4           8             4
  
 [Solution]
 process       AT        BT       Priority        QT        AG        WT        TAT
  P1          0         17          4             4         21        16         33
  P2          3         6           9             4         18        17         23
  P3          4         10          3             4         17        7          17
  P4          29        4           8             4         41        4           8

[Quantum Time Updates]  
  Quantum (4, 4, 4,4) -> ceil(50%) = ( 2,2,2,2) P1 Running
  Quantum (4+1,4,4,4) -> ceil(50%) = ( 3,2,2,2) P2 Running
  Quantum (5,4+2,4 ,4) -> ceil(50%) = ( 3,3,2,2) P3 Running
  Quantum (5,6,4+1,4) -> ceil(50%) = ( 3,3,3,2) P1 Running
  Quantum (5+2,6,5,4) -> ceil(50%) = ( 4,3,3,2) P3 Running
  Quantum (7,6,5+1,4) -> ceil(50%) = ( 4,3,3,2) P2 Running
  Quantum (7,6+3,6,4) -> ceil(50%) = ( 4,5,3,2) P3 Running
  Quantum (7,9,0,4) -> ceil(50%) = ( 4,5,0,2) P1 Running
  Quantum (7+3,9,0,4) -> ceil(50%) = ( 5,5,0,2) P2 Running
  Quantum (10,0,0,4) -> ceil(50%) = ( 5,0,0,2) P1 Running
  Quantum (0,0,0,4) -> ceil(50%) = ( 0,0,0,2) P4 Running
  Quantum (0,0,0,0)

[Processes Execution order]
  P1 P2 P3 P1 P3 P2 P3 P1
  
[Statistics]
  Average turn around time = 20.
  Average waiting time = 11.
