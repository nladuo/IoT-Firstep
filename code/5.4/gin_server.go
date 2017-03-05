package main

import (
  "github.com/gin-gonic/gin"
  "net/http"
)

func main() {
    r := gin.Default()

    r.StaticFS("/static", http.Dir("./static"))

    r.LoadHTMLGlob("./templates/*")
    r.GET("/temp/:city", func(c *gin.Context) {
      city := c.Param("city")

      temps := map[string]string{
        "北京": "23℃",
        "上海": "20℃",
        "哈尔滨": "11℃",
      }

      if temp, ok:= temps[city]; ok {
        c.HTML(200, "index.tmpl", gin.H{
            "city": city,
            "temp": temp,
        })
      } else {
        c.HTML(200, "index.tmpl", gin.H{
            "city": city,
        })
      }
    })


    r.Run(":8000")
}
