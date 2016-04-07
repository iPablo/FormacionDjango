#!/usr/bin/env python
# -*- coding: utf-8 -*-

from __future__ import unicode_literals
from django.db import models
from django.utils import timezone


class Base(models.Model):
	title = models.CharField(max_length=200)

	class Meta:
		abstract = True


class BaseNews(Base):
	description = models.CharField(max_length=200)

	def __unicode__(self):
		return self.title


class NewsItem(BaseNews):
	publish_date = models.DateTimeField('Fecha publicada')

	def ha_pasado(self):  # Es un metodo para comprobar que si las noticias
		# tienen mas de dos dias
		# las seleccione y se vea que son antiguas
		ahora = timezone.now()
		diferencia_dias = ahora-self.publish_date
		if diferencia_dias.days > 2:
			return True
		else:
			return False


class Event(BaseNews):
	start_date = models.DateTimeField('Fecha de inicio')
	end_date = models.DateTimeField('Fecha de fin')

	def dameDuracion(self):  # Devuelve la duracion en dias del evento en cuestion
		if self.end_date < self.start_date:
			self.end_date = self.start_date
		diferencia = self.end_date - self.start_date
		return diferencia.days
