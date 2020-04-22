# aptpeeker
[![Build Status](https://travis-ci.org/mwvdev/aptpeeker.svg?branch=master)](https://travis-ci.org/mwvdev/aptpeeker)
[![codecov](https://codecov.io/gh/mwvdev/aptpeeker/branch/master/graph/badge.svg)](https://codecov.io/gh/mwvdev/aptpeeker)
[![Total alerts](https://img.shields.io/lgtm/alerts/g/mwvdev/aptpeeker.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/mwvdev/aptpeeker/alerts/)

A REST service that can notify Slack when updates to aptitude packages are available.

![Example screenshot](screenshots/slack-notification.png)

# Try it out using Docker
AptPeeker is available on [Docker Hub](https://hub.docker.com/r/mwvdev/aptpeeker).

AptPeeker will need a bridged network to reach Slack when posting notifications, so start by creating a bridged network:

```
$ docker network create external_network
```

Then try it out:

``` bash
$ docker run -e SPRING_SECURITY_USER_PASSWORD=<insert password> -e SLACK_ENDPOINT=<insert slack incoming webhook endpoint> --network external_network mwvdev/aptpeeker
```

Ensure that package lists are periodically updated and that `jq` is installed, then use the following command to report to a locally running instance:

``` bash
$ apt-get upgrade -s | grep ^Inst | awk '{ print $2,$3; }' | tr -d '[]' | jq --compact-output --slurp --raw-input 'split("\n") | map(select(. != ""))' | curl -X POST -u user:<insert password> -H 'Content-type: application/json' --data @- http://localhost:8080/api/package/updates/server-name-goes-here
```

# Credits
This application is heavily inspired by [AptWatcher](https://github.com/honeybadger-io/aptwatcher) by [Honeybadger](https://www.honeybadger.io). 