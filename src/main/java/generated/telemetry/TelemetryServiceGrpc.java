package generated.telemetry;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 * <pre>
 * The telemetry service definition.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.15.0)",
    comments = "Source: TelemetryService.proto")
public final class TelemetryServiceGrpc {

  private TelemetryServiceGrpc() {}

  public static final String SERVICE_NAME = "telemetry.TelemetryService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<generated.telemetry.GetSnapshotRequest,
      generated.telemetry.LoadSnapshot> getGetSnapshotMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetSnapshot",
      requestType = generated.telemetry.GetSnapshotRequest.class,
      responseType = generated.telemetry.LoadSnapshot.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<generated.telemetry.GetSnapshotRequest,
      generated.telemetry.LoadSnapshot> getGetSnapshotMethod() {
    io.grpc.MethodDescriptor<generated.telemetry.GetSnapshotRequest, generated.telemetry.LoadSnapshot> getGetSnapshotMethod;
    if ((getGetSnapshotMethod = TelemetryServiceGrpc.getGetSnapshotMethod) == null) {
      synchronized (TelemetryServiceGrpc.class) {
        if ((getGetSnapshotMethod = TelemetryServiceGrpc.getGetSnapshotMethod) == null) {
          TelemetryServiceGrpc.getGetSnapshotMethod = getGetSnapshotMethod = 
              io.grpc.MethodDescriptor.<generated.telemetry.GetSnapshotRequest, generated.telemetry.LoadSnapshot>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "telemetry.TelemetryService", "GetSnapshot"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generated.telemetry.GetSnapshotRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generated.telemetry.LoadSnapshot.getDefaultInstance()))
                  .setSchemaDescriptor(new TelemetryServiceMethodDescriptorSupplier("GetSnapshot"))
                  .build();
          }
        }
     }
     return getGetSnapshotMethod;
  }

  private static volatile io.grpc.MethodDescriptor<generated.telemetry.StreamLoadRequest,
      generated.telemetry.LoadSample> getStreamLoadMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "StreamLoad",
      requestType = generated.telemetry.StreamLoadRequest.class,
      responseType = generated.telemetry.LoadSample.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<generated.telemetry.StreamLoadRequest,
      generated.telemetry.LoadSample> getStreamLoadMethod() {
    io.grpc.MethodDescriptor<generated.telemetry.StreamLoadRequest, generated.telemetry.LoadSample> getStreamLoadMethod;
    if ((getStreamLoadMethod = TelemetryServiceGrpc.getStreamLoadMethod) == null) {
      synchronized (TelemetryServiceGrpc.class) {
        if ((getStreamLoadMethod = TelemetryServiceGrpc.getStreamLoadMethod) == null) {
          TelemetryServiceGrpc.getStreamLoadMethod = getStreamLoadMethod = 
              io.grpc.MethodDescriptor.<generated.telemetry.StreamLoadRequest, generated.telemetry.LoadSample>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "telemetry.TelemetryService", "StreamLoad"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generated.telemetry.StreamLoadRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generated.telemetry.LoadSample.getDefaultInstance()))
                  .setSchemaDescriptor(new TelemetryServiceMethodDescriptorSupplier("StreamLoad"))
                  .build();
          }
        }
     }
     return getStreamLoadMethod;
  }

  private static volatile io.grpc.MethodDescriptor<generated.telemetry.MeterConfigUpdate,
      generated.telemetry.MeterConfigStatus> getUpdateMeterConfigMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpdateMeterConfig",
      requestType = generated.telemetry.MeterConfigUpdate.class,
      responseType = generated.telemetry.MeterConfigStatus.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<generated.telemetry.MeterConfigUpdate,
      generated.telemetry.MeterConfigStatus> getUpdateMeterConfigMethod() {
    io.grpc.MethodDescriptor<generated.telemetry.MeterConfigUpdate, generated.telemetry.MeterConfigStatus> getUpdateMeterConfigMethod;
    if ((getUpdateMeterConfigMethod = TelemetryServiceGrpc.getUpdateMeterConfigMethod) == null) {
      synchronized (TelemetryServiceGrpc.class) {
        if ((getUpdateMeterConfigMethod = TelemetryServiceGrpc.getUpdateMeterConfigMethod) == null) {
          TelemetryServiceGrpc.getUpdateMeterConfigMethod = getUpdateMeterConfigMethod = 
              io.grpc.MethodDescriptor.<generated.telemetry.MeterConfigUpdate, generated.telemetry.MeterConfigStatus>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "telemetry.TelemetryService", "UpdateMeterConfig"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generated.telemetry.MeterConfigUpdate.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generated.telemetry.MeterConfigStatus.getDefaultInstance()))
                  .setSchemaDescriptor(new TelemetryServiceMethodDescriptorSupplier("UpdateMeterConfig"))
                  .build();
          }
        }
     }
     return getUpdateMeterConfigMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static TelemetryServiceStub newStub(io.grpc.Channel channel) {
    return new TelemetryServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static TelemetryServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new TelemetryServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static TelemetryServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new TelemetryServiceFutureStub(channel);
  }

  /**
   * <pre>
   * The telemetry service definition.
   * </pre>
   */
  public static abstract class TelemetryServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Unary: one request -&gt; one response
     * </pre>
     */
    public void getSnapshot(generated.telemetry.GetSnapshotRequest request,
        io.grpc.stub.StreamObserver<generated.telemetry.LoadSnapshot> responseObserver) {
      asyncUnimplementedUnaryCall(getGetSnapshotMethod(), responseObserver);
    }

    /**
     * <pre>
     * Server streaming: one request -&gt; stream of responses
     * </pre>
     */
    public void streamLoad(generated.telemetry.StreamLoadRequest request,
        io.grpc.stub.StreamObserver<generated.telemetry.LoadSample> responseObserver) {
      asyncUnimplementedUnaryCall(getStreamLoadMethod(), responseObserver);
    }

    /**
     * <pre>
     * Unary: one request -&gt; one response
     * </pre>
     */
    public void updateMeterConfig(generated.telemetry.MeterConfigUpdate request,
        io.grpc.stub.StreamObserver<generated.telemetry.MeterConfigStatus> responseObserver) {
      asyncUnimplementedUnaryCall(getUpdateMeterConfigMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetSnapshotMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                generated.telemetry.GetSnapshotRequest,
                generated.telemetry.LoadSnapshot>(
                  this, METHODID_GET_SNAPSHOT)))
          .addMethod(
            getStreamLoadMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                generated.telemetry.StreamLoadRequest,
                generated.telemetry.LoadSample>(
                  this, METHODID_STREAM_LOAD)))
          .addMethod(
            getUpdateMeterConfigMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                generated.telemetry.MeterConfigUpdate,
                generated.telemetry.MeterConfigStatus>(
                  this, METHODID_UPDATE_METER_CONFIG)))
          .build();
    }
  }

  /**
   * <pre>
   * The telemetry service definition.
   * </pre>
   */
  public static final class TelemetryServiceStub extends io.grpc.stub.AbstractStub<TelemetryServiceStub> {
    private TelemetryServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private TelemetryServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TelemetryServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new TelemetryServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Unary: one request -&gt; one response
     * </pre>
     */
    public void getSnapshot(generated.telemetry.GetSnapshotRequest request,
        io.grpc.stub.StreamObserver<generated.telemetry.LoadSnapshot> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetSnapshotMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Server streaming: one request -&gt; stream of responses
     * </pre>
     */
    public void streamLoad(generated.telemetry.StreamLoadRequest request,
        io.grpc.stub.StreamObserver<generated.telemetry.LoadSample> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getStreamLoadMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Unary: one request -&gt; one response
     * </pre>
     */
    public void updateMeterConfig(generated.telemetry.MeterConfigUpdate request,
        io.grpc.stub.StreamObserver<generated.telemetry.MeterConfigStatus> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getUpdateMeterConfigMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * The telemetry service definition.
   * </pre>
   */
  public static final class TelemetryServiceBlockingStub extends io.grpc.stub.AbstractStub<TelemetryServiceBlockingStub> {
    private TelemetryServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private TelemetryServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TelemetryServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new TelemetryServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Unary: one request -&gt; one response
     * </pre>
     */
    public generated.telemetry.LoadSnapshot getSnapshot(generated.telemetry.GetSnapshotRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetSnapshotMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Server streaming: one request -&gt; stream of responses
     * </pre>
     */
    public java.util.Iterator<generated.telemetry.LoadSample> streamLoad(
        generated.telemetry.StreamLoadRequest request) {
      return blockingServerStreamingCall(
          getChannel(), getStreamLoadMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Unary: one request -&gt; one response
     * </pre>
     */
    public generated.telemetry.MeterConfigStatus updateMeterConfig(generated.telemetry.MeterConfigUpdate request) {
      return blockingUnaryCall(
          getChannel(), getUpdateMeterConfigMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * The telemetry service definition.
   * </pre>
   */
  public static final class TelemetryServiceFutureStub extends io.grpc.stub.AbstractStub<TelemetryServiceFutureStub> {
    private TelemetryServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private TelemetryServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TelemetryServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new TelemetryServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Unary: one request -&gt; one response
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<generated.telemetry.LoadSnapshot> getSnapshot(
        generated.telemetry.GetSnapshotRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetSnapshotMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Unary: one request -&gt; one response
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<generated.telemetry.MeterConfigStatus> updateMeterConfig(
        generated.telemetry.MeterConfigUpdate request) {
      return futureUnaryCall(
          getChannel().newCall(getUpdateMeterConfigMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_SNAPSHOT = 0;
  private static final int METHODID_STREAM_LOAD = 1;
  private static final int METHODID_UPDATE_METER_CONFIG = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final TelemetryServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(TelemetryServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_SNAPSHOT:
          serviceImpl.getSnapshot((generated.telemetry.GetSnapshotRequest) request,
              (io.grpc.stub.StreamObserver<generated.telemetry.LoadSnapshot>) responseObserver);
          break;
        case METHODID_STREAM_LOAD:
          serviceImpl.streamLoad((generated.telemetry.StreamLoadRequest) request,
              (io.grpc.stub.StreamObserver<generated.telemetry.LoadSample>) responseObserver);
          break;
        case METHODID_UPDATE_METER_CONFIG:
          serviceImpl.updateMeterConfig((generated.telemetry.MeterConfigUpdate) request,
              (io.grpc.stub.StreamObserver<generated.telemetry.MeterConfigStatus>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class TelemetryServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    TelemetryServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return generated.telemetry.TelemetryServiceImpl.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("TelemetryService");
    }
  }

  private static final class TelemetryServiceFileDescriptorSupplier
      extends TelemetryServiceBaseDescriptorSupplier {
    TelemetryServiceFileDescriptorSupplier() {}
  }

  private static final class TelemetryServiceMethodDescriptorSupplier
      extends TelemetryServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    TelemetryServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (TelemetryServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new TelemetryServiceFileDescriptorSupplier())
              .addMethod(getGetSnapshotMethod())
              .addMethod(getStreamLoadMethod())
              .addMethod(getUpdateMeterConfigMethod())
              .build();
        }
      }
    }
    return result;
  }
}
