pin = 1 	
duty = 0 
flag = 0	
pwm.setup(pin, 100, duty) 
pwm.start(pin)

function run(  )
	if flag == 0 then
		duty = duty + 5
		if duty >= 1000 then
			flag = 1
		end
	else
		duty = duty - 5
		if duty <= 5 then
			flag = 0
		end
	end
	pwm.setduty(pin, duty)
end
tmr.alarm(0, 5, 1, function() run() end ) 
