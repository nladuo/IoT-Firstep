package main

import (
  "github.com/gin-gonic/gin"
  "fmt"
)

var (
  temp string = "0.0"
)

func main() {
    r := gin.Default()

    //显示温度
    r.GET("/", func(c *gin.Context) {
      c.String(200, fmt.Sprintf("The Temperature is: %s ℃", temp))
    })

    //更新温度
    r.GET("/update", func(c *gin.Context) {
      temp = c.DefaultQuery("temp", "0.0")
      c.String(200, "okay")
    })

    r.Run(":8000")
}
