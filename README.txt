The complexity of the code: O(nm^2) where n is the number of lines and m is the maximum number of words in a line.
We compute a hash value for each possible pattern- 
Given a line and an index of a changed word i, we compute the hash value of the concatenation of the rest of the line's words h(x_i). 
The pair (i, h(x_i)) repesents a pattern, and is stored in a map. The sentences are grouped according to their patterns with a hashmap.
Finally, we print all the sentences stored in the hashmap.
Saving the hash values takes O(m^2) for each line, O(n*m^2) in total.   

We could devide the file into k segments and run k threads. Each thread will compute a different segment.
Because the computation of the hash is independent for each line, we could compute it simultaneously.
The threads would fill the same hashMap, hence we will need to use locks.



