# Dubbo-Uniformity HashSelector

Design and implementation of Dubbo adaptive load balancing with priority random load




## Sample introduction



> Dubbo-based stochastic load balancing




- The random strategy will first determine whether all Invokers have the same weight.

- If they are the same, then the process is relatively simple. Using random. nexInt (length) you can randomly generate an Invoker serial number

- Select the corresponding Invoker according to the serial number. If no weight is set on the service provider, then all Invoker weights are the same, default is 100.

- If the weights are different, then we need to combine the weights to set the random probability.



> Random Load Balancing after Modification

- Get the address of a service from the locally currently registered provider service, and if it is the same as the IP of the current machine, get the provider first.

- If different, re-load the random load and retrieve a provider service