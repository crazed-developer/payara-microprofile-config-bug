# payara-microprofile-config-bug
I believe that Payara 5.184 has a bug. It throws the bug below upon deployment.

Because the bean is not serializeable. This is caused by the Microprofile @ConfigProperty on the Viewscoped bean:

On Wildfly it works as expected.

A quick link to the specification: https://github.com/eclipse/microprofile-config/releases/download/1.3/microprofile-config-spec-1.3.pdf

There are two references to Serializeable in there. 




On Payara this exception is thrown on deployment:
[2019-02-09T16:28:53.473+0100] [Payara 5.184] [SEVERE] [NCLS-CORE-00026] [javax.enterprise.system.core] [tid: _ThreadID=57 _ThreadName=admin-thread-pool::admin-listener(1)] [timeMillis: 1549726133473] [levelValue: 1000] [[
  Exception during lifecycle processing
org.glassfish.deployment.common.DeploymentException: CDI deployment failure:WELD-001413: The bean Managed Bean [class soj.config.ViewScopedBean] with qualifiers [@Default @Any @Named] declares a passivating scope but has a non-passivation-capable dependency WELD%AbstractSyntheticBean%fish.payara.microprofile.config.cdi.ConfigPropertyProducer%null,javax.enterprise.context.Dependent,false[@javax.enterprise.inject.Any()@org.eclipse.microprofile.config.inject.ConfigProperty(defaultValue=org.eclipse.microprofile.config.configproperty.unconfigureddvalue,name=)][][[Ljava.lang.String;,java.lang.String] -- WELD-001413: The bean Managed Bean [class soj.config.ViewScopedBean] with qualifiers [@Default @Any @Named] declares a passivating scope but has a non-passivation-capable dependency WELD%AbstractSyntheticBean%fish.payara.microprofile.config.cdi.ConfigPropertyProducer%null,javax.enterprise.context.Dependent,false[@javax.enterprise.inject.Any()@org.eclipse.microprofile.config.inject.ConfigProperty(defaultValue=org.eclipse.microprofile.config.configproperty.unconfigureddvalue,name=)][][[Ljava.lang.String;,java.lang.String]
	at org.jboss.weld.bootstrap.Validator.validateInjectionPointPassivationCapable(Validator.java:474)
	at org.jboss.weld.bootstrap.Validator.validateInjectionPointForDeploymentProblems(Validator.java:403)
	at org.jboss.weld.bootstrap.Validator.validateInjectionPoint(Validator.java:290)
	at org.jboss.weld.bootstrap.Validator.validateGeneralBean(Validator.java:143)
	at org.jboss.weld.bootstrap.Validator.validateRIBean(Validator.java:164)
	at org.jboss.weld.bootstrap.Validator.validateBean(Validator.java:526)
	at org.jboss.weld.bootstrap.Validator.validateBeans(Validator.java:512)
	at org.jboss.weld.bootstrap.Validator.validateDeployment(Validator.java:487)
	at org.jboss.weld.bootstrap.WeldStartup.validateBeans(WeldStartup.java:490)
	at org.jboss.weld.bootstrap.WeldBootstrap.validateBeans(WeldBootstrap.java:93)
	at org.glassfish.weld.WeldDeployer.processApplicationLoaded(WeldDeployer.java:517)
	at org.glassfish.weld.WeldDeployer.event(WeldDeployer.java:428)
	at org.glassfish.kernel.event.EventsImpl.send(EventsImpl.java:131)
	at org.glassfish.internal.data.ApplicationInfo.load(ApplicationInfo.java:333)
	at com.sun.enterprise.v3.server.ApplicationLifecycle.prepare(ApplicationLifecycle.java:497)
	at org.glassfish.deployment.admin.DeployCommand.execute(DeployCommand.java:540)
	at com.sun.enterprise.v3.admin.CommandRunnerImpl$2$1.run(CommandRunnerImpl.java:549)
	at com.sun.enterprise.v3.admin.CommandRunnerImpl$2$1.run(CommandRunnerImpl.java:545)
	at java.security.AccessController.doPrivileged(Native Method)
	at javax.security.auth.Subject.doAs(Subject.java:360)
	at com.sun.enterprise.v3.admin.CommandRunnerImpl$2.execute(CommandRunnerImpl.java:544)
	at com.sun.enterprise.v3.admin.CommandRunnerImpl$3.run(CommandRunnerImpl.java:575)
	at com.sun.enterprise.v3.admin.CommandRunnerImpl$3.run(CommandRunnerImpl.java:567)
	at java.security.AccessController.doPrivileged(Native Method)
	at javax.security.auth.Subject.doAs(Subject.java:360)
	at com.sun.enterprise.v3.admin.CommandRunnerImpl.doCommand(CommandRunnerImpl.java:566)
	at com.sun.enterprise.v3.admin.CommandRunnerImpl.doCommand(CommandRunnerImpl.java:1475)
	at com.sun.enterprise.v3.admin.CommandRunnerImpl.access$1300(CommandRunnerImpl.java:111)
	at com.sun.enterprise.v3.admin.CommandRunnerImpl$ExecutionContext.execute(CommandRunnerImpl.java:1857)
	at com.sun.enterprise.v3.admin.CommandRunnerImpl$ExecutionContext.execute(CommandRunnerImpl.java:1733)
	at com.sun.enterprise.v3.admin.AdminAdapter.doCommand(AdminAdapter.java:564)
	at com.sun.enterprise.v3.admin.AdminAdapter.onMissingResource(AdminAdapter.java:251)
	at org.glassfish.grizzly.http.server.StaticHttpHandlerBase.service(StaticHttpHandlerBase.java:166)
	at com.sun.enterprise.v3.services.impl.ContainerMapper$HttpHandlerCallable.call(ContainerMapper.java:520)
	at com.sun.enterprise.v3.services.impl.ContainerMapper.service(ContainerMapper.java:217)
	at org.glassfish.grizzly.http.server.HttpHandler.runService(HttpHandler.java:182)
	at org.glassfish.grizzly.http.server.HttpHandler.doHandle(HttpHandler.java:156)
	at org.glassfish.grizzly.http.server.HttpServerFilter.handleRead(HttpServerFilter.java:218)
	at org.glassfish.grizzly.filterchain.ExecutorResolver$9.execute(ExecutorResolver.java:95)
	at org.glassfish.grizzly.filterchain.DefaultFilterChain.executeFilter(DefaultFilterChain.java:260)
	at org.glassfish.grizzly.filterchain.DefaultFilterChain.executeChainPart(DefaultFilterChain.java:177)
	at org.glassfish.grizzly.filterchain.DefaultFilterChain.execute(DefaultFilterChain.java:109)
	at org.glassfish.grizzly.filterchain.DefaultFilterChain.process(DefaultFilterChain.java:88)
	at org.glassfish.grizzly.ProcessorExecutor.execute(ProcessorExecutor.java:53)
	at org.glassfish.grizzly.nio.transport.TCPNIOTransport.fireIOEvent(TCPNIOTransport.java:524)
	at org.glassfish.grizzly.strategies.AbstractIOStrategy.fireIOEvent(AbstractIOStrategy.java:89)
	at org.glassfish.grizzly.strategies.WorkerThreadIOStrategy.run0(WorkerThreadIOStrategy.java:94)
	at org.glassfish.grizzly.strategies.WorkerThreadIOStrategy.access$100(WorkerThreadIOStrategy.java:33)
	at org.glassfish.grizzly.strategies.WorkerThreadIOStrategy$WorkerThreadRunnable.run(WorkerThreadIOStrategy.java:114)
	at org.glassfish.grizzly.threadpool.AbstractThreadPool$Worker.doWork(AbstractThreadPool.java:569)
	at org.glassfish.grizzly.threadpool.AbstractThreadPool$Worker.run(AbstractThreadPool.java:549)
	at java.lang.Thread.run(Thread.java:748)
]]

[2019-02-09T16:28:53.473+0100] [Payara 5.184] [SEVERE] [] [javax.enterprise.system.core] [tid: _ThreadID=57 _ThreadName=admin-thread-pool::admin-listener(1)] [timeMillis: 1549726133473] [levelValue: 1000] [[
  Exception while loading the app]]

[2019-02-09T16:28:53.475+0100] [Payara 5.184] [SEVERE] [AS-WEB-GLUE-00192] [javax.enterprise.web] [tid: _ThreadID=57 _ThreadName=admin-thread-pool::admin-listener(1)] [timeMillis: 1549726133475] [levelValue: 1000] [[
  Undeployment failed for context /payara-microprofile-config-bug-0.1]]

[2019-02-09T16:28:54.381+0100] [Payara 5.184] [INFO] [AS-WEB-GLUE-00172] [javax.enterprise.web] [tid: _ThreadID=132 _ThreadName=Thread-11] [timeMillis: 1549726134381] [levelValue: 800] [[
  Loading application [__admingui] at [/]]]
