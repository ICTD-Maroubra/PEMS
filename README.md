# Portable Energy Management (and Monitoring) System

[![CircleCI](https://circleci.com/gh/ICTD-Maroubra/PEMS/tree/master.svg?style=svg)](https://circleci.com/gh/ICTD-Maroubra/PEMS/tree/master)
[![codecov](https://codecov.io/gh/ICTD-Maroubra/PEMS/branch/master/graph/badge.svg)](https://codecov.io/gh/ICTD-Maroubra/PEMS)

> *"The Portable Energy Management System is designed to maintain human life in rough terrain
for an unlimited amounts of time."* - Dr. David Davis

This repository contains the source for running the PEMS server and user interface. It allows the continuous monitoring and management of systems contained in a *Pod*, alerting its consumers of environmental and power concerns.

# Getting Started

Starting development on PEMS is very simple. All prerequisites freely available, and you don't require a web server such as TomCat to run it.

## Prerequisites

* Java JDK 8 (OpenJDK or Oracle) 
* [MongoDB](https://www.mongodb.com/) &ge; 3.4
* [Yarn](https://yarnpkg.com/en/) &ge; 1.0.0

## Get the Source

```bash
git clone https://github.com/ICTD-Maroubra/PEMS
```

## Server

**NOTE**: The server requires a Linux environment with [BlueZ](http://www.bluez.org/) &ge; 5.37 installed if you wish to make use of the bluetooth monitoring module.

```bash
cd pems-server
./gradlew build
./gradlew run
```

For more documentation on the server's API, you can navigate to `http://HOST:PORT/api-browser` when running the server.

### Configuration

The following table lists environment variables that can be set to configure the server.

| **Option**                | **Default**               | **Description**                                       |
|:--------------------------|---------------------------|:------------------------------------------------------|
|PEMS_HOST                  |0.0.0.0                    |The pems server host IP                                |
|PEMS_PORT                  |9005                       |The pems server port                                   |
|PEMS_MONGODB_CONNECTION    |mongodb://0.0.0.0:27017    |MongoDB connection string                              |
|PEMS_MONGODB_DATABASE      |pems                       |MongoDB database to use                                |
|PEMS_BLE_ENABLED           |false                      |Bluetooth connections enabled (requires Linux w/ Bluez)|

## Interface

```bash
cd pems-interface
yarn install && yarn start
```

# Running Tests

## Server

```bash
cd pems-server
./gradlew test
```

## Interface

```bash
cd pems-interface
yarn install && yarn test
```

# Deployment

Deploying PEMS is a multi stage process:

* API/Monitoring Server
* Interface Application

## Server

PEMS server requires a Linux environment with [BlueZ](http://www.bluez.org/) &ge; 5.37 installed.
An easy way to deploy has been provided through a [Docker](https://www.docker.com/) image.

**NOTE**: Due to issues with device sharing in both MacOS xhyve and Windows Hyper-V virtualization layers, Docker for Mac and Docker for Windows will not be able to make use of the bluetooth devices for monitoring.

First build the image:

```bash
cd pems-server
./gradlew build
cd ../
docker build -t pems-server .
```

Then run the container:

```bash
docker run pems-server
```

## Interface

To build installers:

```bash
cd pems-interface
yarn install && yarn dist
```

# Contributing

TBC

# License

MIT License

Copyright (c) 2017 

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.


# Acknowledgement

This project would not be possible without the contributions made by following individuals:

* Aashish Singla ([@aashishs1994](https://github.com/aashishs1994))
* Ali Kutlu Omeroglu ([@kutluo](https://github.com/kutluo))
* Angze Li ([@lkxlaz](https://github.com/lkxlaz))
* Eric Herkes ([@ericherkes](https://github.com/ericherkes))
* Jacqueline Morgan ([@jacqmorg](https://github.com/jacqmorg))
* Jeremy Yiu ([@Jeremyyiu](https://github.com/Jeremyyiu))
* Liekun Shen ([@alberty4573](https://github.com/alberty4573))
* Mohammad Azimi ([@yasinazimi](https://github.com/yasinazimi))
* Ossama Ghanem
* Prasanth Ambikaipalan ([@PA1984](https://github.com/PA1984))
* Raymond Deng ([@rayziqideng](https://github.com/rayziqideng))
* Robert McDonald
* Smit Patel ([@smit93](https://github.com/smit93))
* Taylor Graham ([@twgraham](https://github.com/twgraham))
* Vishal Uniyal ([@VishalUniyal](https://github.com/VishalUniyal))
* Wei Ke

