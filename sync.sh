#!/bin/bash

rsync -avP --exclude .git --exclude build --exclude .gradle . ubuntu@ec2-54-164-72-113.compute-1.amazonaws.com:plugin-arangodb
