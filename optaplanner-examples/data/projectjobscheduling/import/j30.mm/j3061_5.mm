************************************************************************
file with basedata            : mf61_.bas
initial value random generator: 225054155
************************************************************************
projects                      :  1
jobs (incl. supersource/sink ):  32
horizon                       :  238
RESOURCES
  - renewable                 :  2   R
  - nonrenewable              :  2   N
  - doubly constrained        :  0   D
************************************************************************
PROJECT INFORMATION:
pronr.  #jobs rel.date duedate tardcost  MPM-Time
    1     30      0       28        9       28
************************************************************************
PRECEDENCE RELATIONS:
jobnr.    #modes  #successors   successors
   1        1          3           2   3   4
   2        3          3           7   8  11
   3        3          1           9
   4        3          3           5   6  12
   5        3          2          10  18
   6        3          1          29
   7        3          2          17  22
   8        3          3          12  17  29
   9        3          3          10  13  14
  10        3          2          16  21
  11        3          2          17  28
  12        3          1          23
  13        3          1          19
  14        3          2          15  31
  15        3          2          16  25
  16        3          2          20  26
  17        3          3          24  26  27
  18        3          2          21  23
  19        3          2          20  21
  20        3          2          22  23
  21        3          3          22  25  28
  22        3          1          24
  23        3          2          24  27
  24        3          1          30
  25        3          2          26  27
  26        3          1          30
  27        3          1          30
  28        3          2          29  31
  29        3          1          32
  30        3          1          32
  31        3          1          32
  32        1          0        
************************************************************************
REQUESTS/DURATIONS:
jobnr. mode duration  R 1  R 2  N 1  N 2
------------------------------------------------------------------------
  1      1     0       0    0    0    0
  2      1     3       8    7    6    9
         2     7       4    7    3    9
         3     9       1    5    2    9
  3      1     2       8    9    9    6
         2     8       8    8    9    5
         3     9       7    7    8    4
  4      1     5       7    8    6   10
         2     6       7    5    5   10
         3     8       7    3    2    9
  5      1     3       7    6    6   10
         2     3       8    6    4   10
         3     5       6    6    1   10
  6      1     2       9    5    3    9
         2     6       8    5    2    7
         3     8       8    4    2    7
  7      1     5       9    9    7    8
         2     8       4    8    5    7
         3     8       4    9    6    6
  8      1     1       8   10   10    9
         2     8       8    9    9    7
         3     8       7    9   10    5
  9      1     4       9    6    9    8
         2     5       5    4    9    8
         3     8       3    3    8    7
 10      1     5       9    8    1    6
         2     7       5    7    1    4
         3     9       2    4    1    3
 11      1     4       4    7    5    9
         2     9       3    3    4    9
         3    10       3    2    1    9
 12      1     2       4    7    2    9
         2     2       4    8    2    8
         3     3       3    6    2    8
 13      1     3       9    6    8   10
         2     7       7    5    8    8
         3     7       6    6    8    8
 14      1     1       5    7    9    8
         2     3       5    3    7    6
         3     6       4    2    5    6
 15      1     3       9    5    8    7
         2     6       7    4    4    5
         3     8       6    4    2    5
 16      1     5       8    6    5    9
         2     9       8    6    4    5
         3    10       7    5    2    3
 17      1     5       6    4    1    8
         2     9       6    4    1    7
         3    10       5    4    1    5
 18      1     3       8    5    7    4
         2     5       3    3    6    4
         3     6       2    2    6    3
 19      1     4       4    8   10    5
         2     7       4    3    9    3
         3     7       3    3    9    4
 20      1     1       9    8    9    6
         2     5       7    7    7    6
         3     7       4    2    6    6
 21      1     1       7    8    5    4
         2     6       5    7    5    3
         3    10       4    6    3    2
 22      1     4       9    6    8   10
         2     5       7    5    8    6
         3     8       7    2    7    4
 23      1     1       4    3    9    4
         2     4       3    2    8    3
         3     9       3    1    7    3
 24      1     2       5    9    8    6
         2     7       4    8    8    6
         3     9       2    4    8    5
 25      1     2       9    8   10    2
         2     2       6    8   10    3
         3    10       5    6    9    1
 26      1     5       8    8    8    6
         2     6       8    8    5    5
         3     8       7    7    4    2
 27      1     4       9    9    5    6
         2     8       7    7    4    6
         3    10       6    7    4    5
 28      1     2       9    2    7    6
         2     3       9    2    6    4
         3     4       7    1    2    3
 29      1     4      10    6    5    7
         2     5       7    5    2    5
         3     8       7    3    1    4
 30      1     3       8    9    9   10
         2     4       5    9    7    7
         3     6       3    8    5    6
 31      1     3       7    4    9    6
         2     7       7    3    8    4
         3    10       6    3    8    3
 32      1     0       0    0    0    0
************************************************************************
RESOURCEAVAILABILITIES:
  R 1  R 2  N 1  N 2
   18   19  204  218
************************************************************************
