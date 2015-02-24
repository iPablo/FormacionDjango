from django.db import models
from django.forms import ModelForm


class News(models.Model):
    title = models.CharField(max_length=100)
    description = models.CharField(max_length=300)
    new_date = models.DateTimeField(auto_now_add=True)

    def __unicode__(self):
        return self.title
