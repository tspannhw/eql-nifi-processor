# Apache NiFi Processor for Entity Query Language

[![Build Status](https://travis-ci.org/mtnfog/eql-nifi-processor.png?branch=master)](https://travis-ci.org/mtnfog/eql-nifi-processor)

This project is a custom NiFi processor that enables the evaluation of Entity Query Language (EQL) queries against extracted named entities. This processor can be used in conjunction with the [Idyl E3 NiFi Processor](https://github.com/mtnfog/idyl-e3-nifi-processor). To use this processor, clone the project, build it, and copy the resulting `nar` file to NiFi's `lib` directory.

The project can be built as:

`mvn clean install`

This project is licensed under the Apache Software License, version 2.0.
