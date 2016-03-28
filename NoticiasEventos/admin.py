from django.contrib import admin
from .models import Base, BaseNews, NewsItem, Event

admin.site.register(NewsItem)
admin.site.register(Event)
