package main

import (
	"time"

	"github.com/gin-gonic/gin"
)

type Temperature struct {
	Temp string
	Time string
}

var (
	temps [10]Temperature
)

func main() {
	r := gin.Default()

	r.LoadHTMLGlob("./templates/*")

	//显示温度
	r.GET("/", func(c *gin.Context) {
		c.HTML(200, "index.tmpl", gin.H{})
	})

	//更新温度
	r.GET("/update", func(c *gin.Context) {
		temp := c.DefaultQuery("temp", "0.0")
		shift_temps(temp)
		c.String(200, "okay")
	})

	r.GET("/get_temps", func(c *gin.Context) {
		c.JSON(200, temps)
	})

	r.Run(":8000")
}

func shift_temps(temp string) {
	for i := 0; i < 9; i++ {
		temps[i] = temps[i+1]
	}

	temps[9] = Temperature{
		Temp: temp,
		Time: time.Now().Format("15:04:05"), //golang 诞生在2006-01-02 15:04:05，要按照这个格式来格式化
	}
}
