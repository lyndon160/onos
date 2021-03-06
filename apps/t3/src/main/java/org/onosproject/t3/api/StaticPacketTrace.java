/*
 * Copyright 2017-present Open Networking Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.onosproject.t3.api;

import org.onosproject.net.ConnectPoint;
import org.onosproject.net.DeviceId;
import org.onosproject.net.flow.FlowEntry;
import org.onosproject.net.flow.TrafficSelector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Encapsulates the result of tracing a packet (traffic selector) through
 * the current topology.
 */
public class StaticPacketTrace {

    private final TrafficSelector inPacket;
    private final ConnectPoint in;
    List<List<ConnectPoint>> completePaths;
    private Map<DeviceId, List<GroupsInDevice>> outputsForDevice;
    private Map<DeviceId, List<FlowEntry>> flowsForDevice;
    private StringBuilder resultMessage;

    /**
     * Builds the trace with a given packet and a connect point.
     *
     * @param packet the packet to trace
     * @param in     the initial connect point
     */
    public StaticPacketTrace(TrafficSelector packet, ConnectPoint in) {
        this.inPacket = packet;
        this.in = in;
        completePaths = new ArrayList<>();
        outputsForDevice = new HashMap<>();
        flowsForDevice = new HashMap<>();
        resultMessage = new StringBuilder();
    }

    /**
     * Return the initial packet.
     *
     * @return the initial packet in the form of a selector.
     */
    public TrafficSelector getInitialPacket() {
        return inPacket;
    }

    /**
     * Returns the first connect point the packet came in through.
     *
     * @return the connect point
     */
    public ConnectPoint getInitialConnectPoint() {
        return in;
    }

    /**
     * Add a result message for the Trace.
     *
     * @param resultMessage the message
     */
    public void addResultMessage(String resultMessage) {
        if (this.resultMessage.length() != 0) {
            this.resultMessage.append("\n");
        }
        this.resultMessage.append(resultMessage);
    }

    /**
     * Return the result message.
     *
     * @return the message
     */
    public String resultMessage() {
        return resultMessage.toString();
    }

    /**
     * Adds the groups for a given device.
     *
     * @param deviceId   the device
     * @param outputPath the groups in device objects
     */
    public void addGroupOutputPath(DeviceId deviceId, GroupsInDevice outputPath) {
        if (!outputsForDevice.containsKey(deviceId)) {
            outputsForDevice.put(deviceId, new ArrayList<>());
        }
        outputsForDevice.get(deviceId).add(outputPath);
    }

    /**
     * Returns all the possible group-based outputs for a given device.
     *
     * @param deviceId the device
     * @return the list of Groups for this device.
     */
    public List<GroupsInDevice> getGroupOuputs(DeviceId deviceId) {
        return outputsForDevice.get(deviceId);
    }

    /**
     * Adds a complete possible path.
     *
     * @param completePath the path
     */
    public void addCompletePath(List<ConnectPoint> completePath) {
        completePaths.add(completePath);
    }

    /**
     * Return all the possible path the packet can take through the network.
     *
     * @return a list of paths
     */
    public List<List<ConnectPoint>> getCompletePaths() {
        return completePaths;
    }

    /**
     * Add the flows traversed by the packet in a given device.
     *
     * @param deviceId the device considered
     * @param flows    the flows
     */
    public void addFlowsForDevice(DeviceId deviceId, List<FlowEntry> flows) {
        flowsForDevice.put(deviceId, flows);
    }

    /**
     * Returns the flows matched by this trace's packet for a given device.
     *
     * @param deviceId the device
     * @return the flows matched
     */
    public List<FlowEntry> getFlowsForDevice(DeviceId deviceId) {
        return flowsForDevice.get(deviceId);
    }

    @Override
    public String toString() {
        return "StaticPacketTrace{" +
                "inPacket=" + inPacket +
                ", in=" + in +
                ", completePaths=" + completePaths +
                ", outputsForDevice=" + outputsForDevice +
                ", flowsForDevice=" + flowsForDevice +
                ", resultMessage=" + resultMessage +
                '}';
    }
}
