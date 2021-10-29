# Implementation-of-Bloom-Filter-Counting-Bloom-Filter-and-Coded-Bloom-Filter

About the code:
The source code includes a applrun.java file which has classes applrun which basically runs the file,
BloomFilter class,CountingBloomFilter class,CodedBloomFilter class, conatins s array for the multiple hash functions, numbits is the number of bits in the filter,
numhash is the number of hash functions needed to be created and bitmap being the array to which elements will be hashed into in the cases of
BloomFilter ( the set A and B are created in main) and CountingBloomFilter ( set A is created in main)  whereas the CodedBloomFilter
has the set array and filter array which are two dimensional array which is divided into number of sets and number of filters
respectively and also codes are created and stored in a hashmap. Every class has an encode function for elements to be inserted
into the table and lookup for checking the element exists in the bitmap and CountingBloomFilter has remove function to remove elements from filter.

Input tried:
1) Bloom Filter = number of elements set A and B 1000, number of hash functions 7,number of bits in the filter 10000
2) Counting Bloom Filter = number of elements set A 1000, number of hash functions 7,number of bits in the filter 10000, number of bits to be added/removed 500
3) Coded Bloom Filter = number of sets 3, number of elements each set 1000, number of hash functions 7,number of filters 3,number of bits in each filter 30000

Output:
Output files attached for the above input.

Steps To Run the project:
1) Import the project into an IDE such as Eclipse or IntelliJ and run the mainrun.java file.
2) Enter 1 for Bloom Filter Implementation, 2 for Counting Bloom Filter implementation and 3 for Coded Bloom Filter Implementation.
3) Enter the number of elements and subsequently other parameters when prompted.
4) The output would print the number of elements in the filter in respective sets according to the given requirement.
Also output files are simultaneously created and output is written into the files while printing.
