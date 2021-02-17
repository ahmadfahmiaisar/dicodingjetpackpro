package com.example.submission.abstraction

abstract class Mapper<in I, out O> {
    abstract fun map(input: I): O
}
