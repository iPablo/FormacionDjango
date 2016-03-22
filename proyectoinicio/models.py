from __future__ import unicode_literals
import datetime
from django.db import models

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

class Event(BaseNews):
	start_date= models.DateTimeField('Fecha de inicio')
	end_date=models.DateTimeField('Fecha de fin')
	def dameDuracion(self): #Devuelve la duracion en dias del evento en cuestion
		diferencia = self.end_date - self.start_date
		return diferencia.days


