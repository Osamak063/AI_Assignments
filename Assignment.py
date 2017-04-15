guestList=["Osama","Faiq","Muneeb","Umer"]
for i in range(0,guestList.__len__()):
 print(guestList[i]+str(' you are invited to dinner.'))
print(guestList[0]+str(' is not coming\n'))
guestList[0]="Ali"
for i in range(0,guestList.__len__()):
 print(guestList[i]+str(' you are invited to dinner.'))
print ("Found a bigger dinner table !\nNow inviting more guests.\n")
guestList.insert(1,"Haris")
guestList.insert(0,"Osama")
guestList.append("Asad")
for i in range(0,guestList.__len__()):
 print(guestList[i]+str(' you are invited to dinner.'))
print("\nI have space for only two guests.")
print("Sorry,I can invite only two people for dinner.")
while guestList.__len__()>2:
   removing= guestList.pop()
   print(removing+str(" I'm sorry I can't invite you to dinner."))
print("")
for i in range(0,guestList.__len__()):
 print(guestList[i]+str(' you are still invited to dinner.'))
print("Deleting Remaining elements of Guest List")
del guestList[0:]
for i in range(0,guestList.__len__()):
 print(guestList[i]+str(' you are still invited to dinner.'))
print( "Now " + str(guestList.__len__())+str(" number of people are invited."))