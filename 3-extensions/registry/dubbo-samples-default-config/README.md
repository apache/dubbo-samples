# dubbo-samples-default-config

## Security warning

Warning: this sample includes the `dubbo-rpc-hessian` component, but does not
configure a deserialization whitelist. This means it should never be deployed
in a way that allows untrusted access without first configuring a deserialization
whitelist.

https://dubbo.apache.org/en/docs/notices/security/#some-suggestions-to-deal-with-the-security-vulnerability-of-deserialization
