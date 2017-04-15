pizzas=["Chicken Tikka","Chicken Tandoori","Afghani Feast","Phantom","Chicken Fajita"]
friends_pizzas=pizzas[:]
friends_pizzas.insert(0,"Super Creamy")
print("My favorite pizzas are:")
for i in range(0,pizzas.__len__()):
    print(pizzas[i])
print("My friend's favorite pizzas are:")
for i in range(0,pizzas.__len__()):
    print(friends_pizzas[i])