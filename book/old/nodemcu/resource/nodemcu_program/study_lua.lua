tab = {} 
tab[1] = "Hi"		
tab[2] = "Hello"		
tab.author = "nladuo" 
tab["Count"] = 4	
-- haah
for k, v in pairs(tab) do
    print(k, v)
end