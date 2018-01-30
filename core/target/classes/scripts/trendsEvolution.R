#!/usr/bin/env Rscript

library(ggplot2)
library(gridExtra)
library(ggthemes)


#------------- Read data ----------

data = read.csv("${data.origin}", header = TRUE,sep=';')

map_facets<-c(
<#list data.names as propName, propValue>
  "${propName}"="${propValue}"<#if propValue?has_next>,</#if>
</#list>
)

#------------- Generate Graph ----------

plot<-ggplot(data, aes(x = as.numeric(year), y = papers))  +
  theme_bw() +
  geom_line(aes(linetype=factor(type)),stat = "identity") +
  geom_point(mapping=aes(x=as.numeric(year), y=papers, shape=factor(type)), size=3)+
  scale_linetype_discrete(labels=map_facets,guide=FALSE)+
  scale_shape_discrete(labels=map_facets)+
  scale_y_continuous(labels = scales::percent)+
  ylab("# ${data.y_name} papers/ # year papers") + 
  xlab("Year of publication") + 
  labs(shape = "${data.y_name}")+
  theme(legend.position="bottom",legend.text=element_text(size=15)) +
  scale_x_continuous(breaks=c(<#list data.years as year>${year}<#if year?has_next>,</#if></#list>)) + theme(aspect.ratio=0.45)+
  theme(text = element_text(size=13),axis.text.x = element_text(angle=60, hjust=1))+guides(shape=guide_legend(nrow=1,byrow=TRUE))
#------------- Write to pdf ----------

ggsave("${data.destination}", plot, width = 12)