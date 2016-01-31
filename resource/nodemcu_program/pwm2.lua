rPin = 1
gPin = 2
bPin = 8	
rDuty = 1000
gDuty = 0
bDuty = 0 
flag = 0	
pwm.setup(rPin, 100, rDuty) 
pwm.setup(gPin, 100, gDuty) 
pwm.setup(bPin, 100, bDuty) 
pwm.start(rPin)
pwm.start(gPin)
pwm.start(bPin)

function run(  )
	if flag == 0 then		
		gDuty = gDuty + 5
		if gDuty >= 1000 then
			flag = 1
		end
	elseif flag == 1 then
		rDuty = rDuty - 5
		if rDuty <= 5 then
			flag = 2
		end
	elseif flag == 2 then	
		bDuty = bDuty + 5
		if bDuty >= 1000 then
			flag = 3
		end
	elseif flag == 3 then	
		gDuty = gDuty - 5
		if gDuty <= 5 then
			flag = 4
		end
	elseif flag == 4 then	
		rDuty = rDuty + 5
		if rDuty >= 1000 then
			flag = 5
		end
	elseif flag == 5 then			
		bDuty = bDuty - 5
		if bDuty <= 5 then
			flag = 0
		end
	end
	pwm.setduty(rPin, rDuty)
	pwm.setduty(gPin, gDuty)
	pwm.setduty(bPin, bDuty)
end
tmr.alarm(0, 10, 1, function() run() end ) 
