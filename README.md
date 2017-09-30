# Portable Energy Management (and Monitoring) System

[![CircleCI](https://circleci.com/gh/ICTD-Maroubra/PEMS/tree/master.svg?style=svg)](https://circleci.com/gh/ICTD-Maroubra/PEMS/tree/master)

> *"The Portable Energy Management System is designed to maintain human life in rough terrain
for an unlimited amounts of time."* - Dr. David Davis

This repository contains the source for running the PEMS server and user interface. It allows the continuous monitoring and management of systems contained in a *Pod*, alerting its consumers of environmental and power concerns.

# Getting Started

## Prerequisites

* Java JDK 8 (OpenJDK or Oracle) 
* [MongoDB](https://www.mongodb.com/) &ge; 3.4
* [Yarn](https://yarnpkg.com/en/) &ge; 1.0.0

## Get the Source

```bash
git clone https://github.com/ICTD-Maroubra/PEMS
```

## Server

```bash
cd pems-server
./gradlew build
./gradlew run
```

### Configuration

```
PEMS_HOST - The pems server host IP (default: 0.0.0.0)
PEMS_PORT - The pems server port (default: 9005)
```

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

First build the image:

```bash
cd pems-server
./gradlew build
cd ../
docker build -t pems-server .
```

Then run the image:

```bash
docker run pems-server
```

# Contributing

TBC

# License

TBC

# Acknowledgement

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

