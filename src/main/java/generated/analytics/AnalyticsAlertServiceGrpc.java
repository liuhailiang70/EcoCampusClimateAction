package generated.analytics;

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
 * The analytics alert service definition.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.15.0)",
    comments = "Source: AnalyticsAlertService.proto")
public final class AnalyticsAlertServiceGrpc {

  private AnalyticsAlertServiceGrpc() {}

  public static final String SERVICE_NAME = "analytics.AnalyticsAlertService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<generated.analytics.BaselineRequest,
      generated.analytics.BaselineModel> getGetBaselineMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetBaseline",
      requestType = generated.analytics.BaselineRequest.class,
      responseType = generated.analytics.BaselineModel.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<generated.analytics.BaselineRequest,
      generated.analytics.BaselineModel> getGetBaselineMethod() {
    io.grpc.MethodDescriptor<generated.analytics.BaselineRequest, generated.analytics.BaselineModel> getGetBaselineMethod;
    if ((getGetBaselineMethod = AnalyticsAlertServiceGrpc.getGetBaselineMethod) == null) {
      synchronized (AnalyticsAlertServiceGrpc.class) {
        if ((getGetBaselineMethod = AnalyticsAlertServiceGrpc.getGetBaselineMethod) == null) {
          AnalyticsAlertServiceGrpc.getGetBaselineMethod = getGetBaselineMethod = 
              io.grpc.MethodDescriptor.<generated.analytics.BaselineRequest, generated.analytics.BaselineModel>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "analytics.AnalyticsAlertService", "GetBaseline"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generated.analytics.BaselineRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generated.analytics.BaselineModel.getDefaultInstance()))
                  .setSchemaDescriptor(new AnalyticsAlertServiceMethodDescriptorSupplier("GetBaseline"))
                  .build();
          }
        }
     }
     return getGetBaselineMethod;
  }

  private static volatile io.grpc.MethodDescriptor<generated.analytics.MitigationCommand,
      generated.analytics.MitigationFeedback> getMitigationLoopMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "MitigationLoop",
      requestType = generated.analytics.MitigationCommand.class,
      responseType = generated.analytics.MitigationFeedback.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<generated.analytics.MitigationCommand,
      generated.analytics.MitigationFeedback> getMitigationLoopMethod() {
    io.grpc.MethodDescriptor<generated.analytics.MitigationCommand, generated.analytics.MitigationFeedback> getMitigationLoopMethod;
    if ((getMitigationLoopMethod = AnalyticsAlertServiceGrpc.getMitigationLoopMethod) == null) {
      synchronized (AnalyticsAlertServiceGrpc.class) {
        if ((getMitigationLoopMethod = AnalyticsAlertServiceGrpc.getMitigationLoopMethod) == null) {
          AnalyticsAlertServiceGrpc.getMitigationLoopMethod = getMitigationLoopMethod = 
              io.grpc.MethodDescriptor.<generated.analytics.MitigationCommand, generated.analytics.MitigationFeedback>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "analytics.AnalyticsAlertService", "MitigationLoop"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generated.analytics.MitigationCommand.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generated.analytics.MitigationFeedback.getDefaultInstance()))
                  .setSchemaDescriptor(new AnalyticsAlertServiceMethodDescriptorSupplier("MitigationLoop"))
                  .build();
          }
        }
     }
     return getMitigationLoopMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static AnalyticsAlertServiceStub newStub(io.grpc.Channel channel) {
    return new AnalyticsAlertServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static AnalyticsAlertServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new AnalyticsAlertServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static AnalyticsAlertServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new AnalyticsAlertServiceFutureStub(channel);
  }

  /**
   * <pre>
   * The analytics alert service definition.
   * </pre>
   */
  public static abstract class AnalyticsAlertServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Unary: one request -&gt; one response
     * </pre>
     */
    public void getBaseline(generated.analytics.BaselineRequest request,
        io.grpc.stub.StreamObserver<generated.analytics.BaselineModel> responseObserver) {
      asyncUnimplementedUnaryCall(getGetBaselineMethod(), responseObserver);
    }

    /**
     * <pre>
     * Bidirectional streaming: stream of requests &lt;-&gt; stream of responses
     * </pre>
     */
    public io.grpc.stub.StreamObserver<generated.analytics.MitigationCommand> mitigationLoop(
        io.grpc.stub.StreamObserver<generated.analytics.MitigationFeedback> responseObserver) {
      return asyncUnimplementedStreamingCall(getMitigationLoopMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetBaselineMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                generated.analytics.BaselineRequest,
                generated.analytics.BaselineModel>(
                  this, METHODID_GET_BASELINE)))
          .addMethod(
            getMitigationLoopMethod(),
            asyncBidiStreamingCall(
              new MethodHandlers<
                generated.analytics.MitigationCommand,
                generated.analytics.MitigationFeedback>(
                  this, METHODID_MITIGATION_LOOP)))
          .build();
    }
  }

  /**
   * <pre>
   * The analytics alert service definition.
   * </pre>
   */
  public static final class AnalyticsAlertServiceStub extends io.grpc.stub.AbstractStub<AnalyticsAlertServiceStub> {
    private AnalyticsAlertServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private AnalyticsAlertServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AnalyticsAlertServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new AnalyticsAlertServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Unary: one request -&gt; one response
     * </pre>
     */
    public void getBaseline(generated.analytics.BaselineRequest request,
        io.grpc.stub.StreamObserver<generated.analytics.BaselineModel> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetBaselineMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Bidirectional streaming: stream of requests &lt;-&gt; stream of responses
     * </pre>
     */
    public io.grpc.stub.StreamObserver<generated.analytics.MitigationCommand> mitigationLoop(
        io.grpc.stub.StreamObserver<generated.analytics.MitigationFeedback> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(getMitigationLoopMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   * <pre>
   * The analytics alert service definition.
   * </pre>
   */
  public static final class AnalyticsAlertServiceBlockingStub extends io.grpc.stub.AbstractStub<AnalyticsAlertServiceBlockingStub> {
    private AnalyticsAlertServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private AnalyticsAlertServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AnalyticsAlertServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new AnalyticsAlertServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Unary: one request -&gt; one response
     * </pre>
     */
    public generated.analytics.BaselineModel getBaseline(generated.analytics.BaselineRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetBaselineMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * The analytics alert service definition.
   * </pre>
   */
  public static final class AnalyticsAlertServiceFutureStub extends io.grpc.stub.AbstractStub<AnalyticsAlertServiceFutureStub> {
    private AnalyticsAlertServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private AnalyticsAlertServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AnalyticsAlertServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new AnalyticsAlertServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Unary: one request -&gt; one response
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<generated.analytics.BaselineModel> getBaseline(
        generated.analytics.BaselineRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetBaselineMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_BASELINE = 0;
  private static final int METHODID_MITIGATION_LOOP = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AnalyticsAlertServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(AnalyticsAlertServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_BASELINE:
          serviceImpl.getBaseline((generated.analytics.BaselineRequest) request,
              (io.grpc.stub.StreamObserver<generated.analytics.BaselineModel>) responseObserver);
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
        case METHODID_MITIGATION_LOOP:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.mitigationLoop(
              (io.grpc.stub.StreamObserver<generated.analytics.MitigationFeedback>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class AnalyticsAlertServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    AnalyticsAlertServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return generated.analytics.AnalyticsAlertServiceImpl.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("AnalyticsAlertService");
    }
  }

  private static final class AnalyticsAlertServiceFileDescriptorSupplier
      extends AnalyticsAlertServiceBaseDescriptorSupplier {
    AnalyticsAlertServiceFileDescriptorSupplier() {}
  }

  private static final class AnalyticsAlertServiceMethodDescriptorSupplier
      extends AnalyticsAlertServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    AnalyticsAlertServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (AnalyticsAlertServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new AnalyticsAlertServiceFileDescriptorSupplier())
              .addMethod(getGetBaselineMethod())
              .addMethod(getMitigationLoopMethod())
              .build();
        }
      }
    }
    return result;
  }
}
