Name: Chengyang Zhong<br>
Student ID: 20215708

### Instructions: 
    Start the program in IDEA, then user need to input the file name 
    to load instance, and then the program will automatically generate
    the best solutions and best objective value for each trail

### Configuration
    Users can change the parameter settings like generations, IoM and 
    DoS in main class.

### Some Notes of Project
    1. Instance object means an item which has its own weight, profit
       and state. It contains related operations on these fields. 

       Solution object contains an array of Instance, which represent
       all the items you can pick up. It contains related operations
       on these fields
       
       Problem object contains different solutions as individuals, memes
       which represent different types of different heuristics and many 
       auxilary fields. It also contains operations on these fields
    2. All heuristic or methods used in Multimeme Algorithm has been 
       treated as meme.
    3. When the total weight of solution is greater than boundary, use
       a penalization to lower the objective function, but remain the 
       possibility to be selected by selection methods
    4. Delta evaluation is implemented in Problem class, HillClimb and
       Mutation can use it whenever they need
    5. After initializing the solutions when loading instance or creating
       a new Problem object, the greedy heuristic search will be applied 
       on these solutions and generate better parent solutions