package main

import (
	"fmt"
	"net/http"
)

func Handle(rw http.ResponseWriter, req *http.Request) {
	fmt.Fprintln(rw, "<h1>Hello World!</h1>")
}

func main() {
	http.HandleFunc("/", Handle)
	err := http.ListenAndServe(":8000", nil)
	if err != nil { //如果出现了错误，err就不为空
		panic(err)
	}
}
