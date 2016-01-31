uart.setup(0, 9600, 8, 0, 1, 0)
uart.on("data",
      function(data)
        print("receive from uart:", data)
        if data=="quit" then 
          uart.on("data") 
        end
    end, 0)