#!/bin/bash


for i in `seq 1 10000`; 
do
    ./sentence_generator.py 
    sleep 0.5;
done
