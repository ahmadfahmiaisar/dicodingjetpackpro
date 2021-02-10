package com.example.submission1.abstraction

abstract class Mapper<in I, out O> {
    abstract fun map(input: I): O
}
