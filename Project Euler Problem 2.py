a=1
b=2
c=0
count=0
sum=0
print("Fibonacci Series:")
print(a)
while count<=9:
    print(b)
    if(b%2==0):
     sum=sum+b
    c=b+a
    a=b
    b=c
    count=count+1
print("Sum of the even valued terms:")
print(sum)