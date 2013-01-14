package com.bazaarvoice.zookeeper.dropwizard;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Supplier;
import com.netflix.curator.RetryPolicy;

/**
 * ZooKeeper connection configuration class.
 * <p/>
 * This class is designed to map easily to YAML configuration files, deserialized using Jackson.
 */
public class ZooKeeperConfiguration extends com.bazaarvoice.zookeeper.ZooKeeperConfiguration {
    /**
     * Sets a ZooKeeper connection string that looks like "host:port,host:port,...".  The
     * connection string must list at least one live member of the ZooKeeper ensemble, and
     * should list all members of the ZooKeeper ensemble in case any one member is temporarily
     * unavailable.
     *
     * @param connectString A ZooKeeper connection string.
     */
    @JsonProperty
    public ZooKeeperConfiguration setConnectString(String connectString) {
        withConnectString(connectString);
        return this;
    }

    @VisibleForTesting
    protected void setConnectStringSupplier(Supplier<String> supplier) {
        super.setConnectStringSupplier(supplier);
    }

    /**
     * Sets a retry policy that retries a set number of times with increasing sleep time between retries up to a
     * maximum sleep time.
     */
    @JsonProperty
    public ZooKeeperConfiguration setRetry(BoundedExponentialBackoffRetry retry) {
        withBoundedExponentialBackoffRetry(retry.baseSleepTimeMs, retry.maxSleepTimeMs, retry.maxAttempts);
        return this;
    }

    @VisibleForTesting
    protected RetryPolicy getRetryPolicy() {
        return super.getRetryPolicy();
    }

    /**
     * Sets a namespace that will be prefixed to every path used by the ZooKeeperConnection.
     * Typically the namespace will be "/global" or the name of the local data center.  If non-empty, the namespace
     * must be a valid ZooKeeper path (starts with '/', does not end with '/', etc).
     */
    @JsonProperty
    public ZooKeeperConfiguration setNamespace(String namespace) {
        withNamespace(namespace);
        return this;
    }

    @VisibleForTesting
    protected String getNamespace() {
        return super.getNamespace();
    }
}
