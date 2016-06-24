pin=1
gpio.mode(pin, gpio.OUTPUT)

isLight = false
function run()
	if isLight then
		gpio.write(pin, gpio.LOW)
	else
		gpio.write(pin, gpio.HIGH)
	end
	isLight = not isLight
end

tmr.alarm(0, 1000, 1, function() run() end )